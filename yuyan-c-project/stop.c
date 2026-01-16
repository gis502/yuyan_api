#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <direct.h>
#include <locale.h>

// 设置控制台编码为UTF-8
void setConsoleEncoding() {
    SetConsoleOutputCP(65001);
    SetConsoleCP(65001);
    setlocale(LC_ALL, "zh_CN.UTF-8");
}

// 检查管理员权限
int isAdmin() {
    HANDLE token;
    if (!OpenProcessToken(GetCurrentProcess(), TOKEN_QUERY, &token)) {
        return 0;
    }

    TOKEN_ELEVATION elevation;
    DWORD size;
    if (!GetTokenInformation(token, TokenElevation, &elevation, sizeof(elevation), &size)) {
        CloseHandle(token);
        return 0;
    }

    CloseHandle(token);
    return elevation.TokenIsElevated;
}

// 根据端口号获取并终止进程
int killProcessByPort(int port) {
    char cmd[256];
    sprintf(cmd, "netstat -ano | findstr \":%d \"", port);

    FILE *fp = _popen(cmd, "r");
    if (!fp) return 0;

    char line[512];
    DWORD pid = 0;

    // 获取第一个匹配的PID
    while (fgets(line, sizeof(line), fp)) {
        line[strcspn(line, "\r\n")] = 0;
        char *lastSpace = strrchr(line, ' ');
        if (lastSpace) {
            pid = atoi(lastSpace + 1);
            break;
        }
    }

    _pclose(fp);

    if (pid != 0) {
        // 使用taskkill终止指定PID的进程
        sprintf(cmd, "taskkill /F /PID %lu >nul 2>&1", pid);
        int result = system(cmd);

        // 等待进程结束
        Sleep(1000);

        return result == 0;
    }

    return 0;
}

// 检查端口是否被监听
int isPortListening(int port) {
    char cmd[256];
    sprintf(cmd, "netstat -ano | findstr \":%d \"", port);

    FILE *fp = _popen(cmd, "r");
    if (!fp) return 0;

    char line[512];
    int listening = 0;

    while (fgets(line, sizeof(line), fp)) {
        if (strstr(line, "LISTENING")) {
            listening = 1;
            break;
        }
    }

    _pclose(fp);
    return listening;
}

// 使用nginx命令停止服务
int stopNginxService() {
    char oldDir[MAX_PATH];
    GetCurrentDirectory(MAX_PATH, oldDir);

    // 切换到nginx目录
    if (!SetCurrentDirectory("nginx")) {
        printf("无法切换到nginx目录\n");
        return 0;
    }

    // 使用nginx -s quit命令停止服务
    int result = system("nginx.exe -s quit");

    // 等待一段时间让nginx开始退出过程
    Sleep(1000);

    // 恢复原始目录
    SetCurrentDirectory(oldDir);

    return result == 0;
}

// 检查nginx进程是否存在
int isNginxRunning() {
    FILE *fp = _popen("tasklist /FI \"IMAGENAME eq nginx.exe\" /FO CSV", "r");
    if (!fp) return 0;

    char buffer[512];
    int found = 0;

    // 读取第一行（标题）
    if (fgets(buffer, sizeof(buffer), fp)) {
        // 读取后续行
        while (fgets(buffer, sizeof(buffer), fp)) {
            if (strstr(buffer, "nginx.exe")) {
                found = 1;
                break;
            }
        }
    }

    _pclose(fp);
    return found;
}

// 动态检测Nginx是否已停止
int waitForNginxToStop(int maxWaitSeconds) {
    printf("正在等待Nginx服务停止");

    for (int i = 0; i < maxWaitSeconds; i++) {
        // 检查nginx进程是否还在运行
        if (!isNginxRunning() && !isPortListening(7000)) {
            printf("\n");
            return 1; // Nginx已停止
        }

        // 显示等待指示器
        switch(i % 4) {
            case 0: printf("."); break;
            case 1: printf("o"); break;
            case 2: printf("O"); break;
            case 3: printf("o"); break;
        }
        fflush(stdout);

        Sleep(1000); // 等待1秒
    }

    printf("\n");
    return 0; // 超时，Nginx仍未停止
}

// 打印带有颜色的文本
void printColor(int color, const char* text) {
    HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
    SetConsoleTextAttribute(hConsole, color);
    printf("%s", text);
    SetConsoleTextAttribute(hConsole, 7); // 恢复默认颜色
}

// 打印带图标的行
void printLine(const char* icon, const char* text, int isError) {
    if (isError) {
        printColor(12, icon); // 红色
        printf(" %s\n", text);
    } else {
        printColor(10, icon); // 绿色
        printf(" %s\n", text);
    }
}

// 打印标题
void printHeader() {
    system("cls");
    printColor(11, "\n============================================\n");
    printColor(14, "        雨燕应急系统 - 服务停止器\n");
    printColor(11, "============================================\n\n");
}

// 打印分隔线
void printDivider() {
    printColor(8, "--------------------------------------------\n");
}

int main() {
    // 设置编码
    setConsoleEncoding();

    // 检查权限
    if (!isAdmin()) {
        system("cls");
        printColor(12, "\n[权限错误]\n");
        printf("请右键点击程序，选择「以管理员身份运行」\n");
        printf("需要管理员权限才能停止系统服务\n\n");
        system("pause");
        return 1;
    }

    // 切换到程序目录
    char exePath[MAX_PATH];
    GetModuleFileName(NULL, exePath, MAX_PATH);
    char *lastSlash = strrchr(exePath, '\\');
    if (lastSlash) *lastSlash = '\0';
    SetCurrentDirectory(exePath);

    // 显示标题
    printHeader();

    printf("正在停止服务...\n");
    printDivider();

    // 首先尝试使用nginx内置命令停止Nginx服务
    if (isPortListening(7000)) {
        printf("正在停止Nginx服务...\n");
        if (stopNginxService()) {
            printLine("[✓]", "Nginx服务停止命令已发送", 0);

            // 动态等待Nginx停止
            if (waitForNginxToStop(15)) { // 最多等待15秒
                printLine("[✓]", "Nginx服务已完全停止", 0);
            } else {
                printLine("[!]", "Nginx服务停止超时", 0);
            }
        } else {
            printLine("[!]", "Nginx服务停止命令发送失败", 0);
        }
    } else {
        printLine("[✓]", "Nginx服务未运行或已在端口7000上停止", 0);
    }

    printDivider();

    // 停止Java服务 - 通过端口7001
    if (isPortListening(7001)) {
        printf("停止Java服务 (端口: 7001)...\n");

        if (killProcessByPort(7001)) {
            printLine("[✓]", "Java服务已停止", 0);
        } else {
            printLine("[!]", "Java服务停止可能失败", 0);
        }
    } else {
        printLine("[✓]", "Java服务未运行或已在端口7001上停止", 0);
    }

    printDivider();

    // 停止管理界面服务 - 通过端口7002（如果存在）
    if (isPortListening(7002)) {
        printf("停止管理界面服务 (端口: 7002)...\n");

        if (killProcessByPort(7002)) {
            printLine("[✓]", "管理界面服务已停止", 0);
        } else {
            printLine("[!]", "管理界面服务停止可能失败", 0);
        }
    } else {
        printLine("[✓]", "管理界面服务未运行或已在端口7002上停止", 0);
    }

    printDivider();

    // 最终检查
    printf("最终状态检查...\n");
    int nginxRunning = isNginxRunning() || isPortListening(7000);
    int javaRunning = isPortListening(7001);
    int adminRunning = isPortListening(7002);

    if (nginxRunning) {
        printLine("[✗]", "警告: Nginx仍在运行 (端口 7000)", 1);
    }
    if (javaRunning) {
        printLine("[✗]", "警告: Java仍在运行 (端口 7001)", 1);
    }
    if (adminRunning) {
        printLine("[✗]", "警告: 管理界面仍在运行 (端口 7002)", 1);
    }

    if (!nginxRunning && !javaRunning && !adminRunning) {
        printLine("[✓]", "所有服务已完全停止", 0);
    } else {
        printLine("[!]", "部分服务仍可能在运行，建议手动检查", 1);
    }

    printDivider();
    printColor(14, "\n服务停止完成！\n\n");
    printColor(8, "注意：所有系统服务已停止\n");
    printColor(8, "如需重新启动，请运行 start.exe\n\n");

    printf("按任意键关闭窗口...");
    system("pause > nul");
    return 0;
}