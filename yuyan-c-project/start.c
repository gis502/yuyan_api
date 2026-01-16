#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <direct.h>
#include <locale.h>
#include <psapi.h>

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

// 获取进程PID通过端口
DWORD getPidByPort(int port) {
    char cmd[256];
    sprintf(cmd, "netstat -ano | findstr \":%d \"", port);

    FILE *fp = _popen(cmd, "r");
    if (!fp) return 0;

    char line[512];
    DWORD pid = 0;

    while (fgets(line, sizeof(line), fp)) {
        line[strcspn(line, "\r\n")] = 0;
        char *lastSpace = strrchr(line, ' ');
        if (lastSpace) {
            pid = atoi(lastSpace + 1);
            break;
        }
    }

    _pclose(fp);
    return pid;
}

// 检查端口是否被监听
int isPortListening(int port) {
    DWORD pid = getPidByPort(port);
    return pid != 0;
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

// 打印进度条
void printProgress(const char* text, int current, int total) {
    printf("%s [", text);

    int barWidth = 20;
    int progress = (current * barWidth) / total;

    for (int i = 0; i < barWidth; i++) {
        if (i < progress) {
            printColor(10, "="); // 绿色进度
        } else {
            printf(" ");
        }
    }

    printf("] %d%%\n", (current * 100) / total);
}

// 打印标题
void printHeader() {
    system("cls");
    printColor(11, "\n============================================\n");
    printColor(14, "        雨燕应急系统 - 服务启动器\n");
    printColor(11, "============================================\n\n");
}

// 打印分隔线
void printDivider() {
    printColor(8, "--------------------------------------------\n");
}

// 启动Nginx服务
int startNginx() {
    printf("启动Nginx服务...\n");

    char oldDir[MAX_PATH];
    GetCurrentDirectory(MAX_PATH, oldDir);

    if (!SetCurrentDirectory("nginx")) {
        printLine("[错误]", "无法切换到nginx目录", 1);
        return 0;
    }

    int result = system("start nginx.exe");
    SetCurrentDirectory(oldDir);

    return result == 0;
}

// 启动Java服务
int startJava() {
    printf("启动Java后端服务...\n");

    char javaCmd[MAX_PATH * 2];
    snprintf(javaCmd, sizeof(javaCmd),
             "start \"Java服务\" /min openjdk17\\bin\\javaw.exe -jar \"后台管理\\back\\admin.jar\"");

    int result = system(javaCmd);
    return result == 0;
}

int main() {
    // 设置编码
    setConsoleEncoding();

    // 检查权限
    if (!isAdmin()) {
        system("cls");
        printColor(12, "\n[权限错误]\n");
        printf("请右键点击程序，选择「以管理员身份运行」\n");
        printf("需要管理员权限才能启动系统服务\n\n");
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

    // 检查文件
    printf("检查必要文件...\n");
    printDivider();

    int allFilesOk = 1;

    if (_access("nginx\\nginx.exe", 0) != 0) {
        printLine("[×]", "nginx.exe 文件缺失", 1);
        allFilesOk = 0;
    } else {
        printLine("[✓]", "nginx.exe 文件正常", 0);
    }

    if (_access("openjdk17\\bin\\java.exe", 0) != 0) {
        printLine("[×]", "java.exe 文件缺失", 1);
        allFilesOk = 0;
    } else {
        printLine("[✓]", "java.exe 文件正常", 0);
    }

    if (_access("后台管理\\back\\admin.jar", 0) != 0) {
        printLine("[×]", "admin.jar 文件缺失", 1);
        allFilesOk = 0;
    } else {
        printLine("[✓]", "admin.jar 文件正常", 0);
    }

    if (!allFilesOk) {
        printDivider();
        printColor(12, "\n错误：请检查上述缺失的文件\n\n");
        system("pause");
        return 1;
    }

    printDivider();

    // 检查端口
    printf("检查端口占用情况...\n");

    if (isPortListening(7000)) {
        printLine("[×]", "端口 7000 已被占用", 1);
        allFilesOk = 0;
    } else {
        printLine("[✓]", "端口 7000 可用", 0);
    }

    if (isPortListening(7001)) {
        printLine("[×]", "端口 7001 已被占用", 1);
        allFilesOk = 0;
    } else {
        printLine("[✓]", "端口 7001 可用", 0);
    }

    if (isPortListening(7002)) {
        printLine("[×]", "端口 7002 已被占用", 1);
        allFilesOk = 0;
    } else {
        printLine("[✓]", "端口 7002 可用", 0);
    }

    if (!allFilesOk) {
        printDivider();
        printColor(12, "\n错误：请先释放被占用的端口\n\n");
        system("pause");
        return 1;
    }

    printDivider();

    // 启动Nginx
    if (!startNginx()) {
        printLine("[×]", "Nginx启动失败", 1);
        system("pause");
        return 1;
    }

    // 等待Nginx启动并检测端口
    printf("等待Nginx启动...\n");
    int nginxStarted = 0;

    for (int i = 0; i < 8; i++) {
        Sleep(1000);
        printProgress("Nginx启动进度", i+1, 8);
        if (isPortListening(7000)) {
            nginxStarted = 1;
            break;
        }
    }

    if (nginxStarted) {
        printLine("[✓]", "Nginx服务已启动", 0);
    } else {
        printLine("[!]", "Nginx启动可能失败，请检查日志", 1);
    }

    printDivider();

    // 启动Java
    if (!startJava()) {
        printLine("[×]", "Java服务启动失败", 1);
    } else {
        // 等待Java启动并检测端口
        printf("等待Java启动...\n");
        int javaStarted = 0;

        for (int i = 0; i < 10; i++) {
            Sleep(1000);
            printProgress("Java启动进度", i+1, 10);
            if (isPortListening(7001)) {
                javaStarted = 1;
                break;
            }
        }

        if (javaStarted) {
            printLine("[✓]", "Java服务已启动", 0);
        } else {
            printLine("[!]", "Java启动可能较慢，请稍后检查", 0);
        }
    }

    printDivider();

    // 显示服务信息
    printColor(14, "\n服务启动完成！\n\n");

    printf("访问地址：\n");
    printColor(11, "  前端服务: http://localhost:7000\n");
    printColor(11, "  后端服务: http://localhost:7001\n");
    printColor(11, "  管理界面: http://localhost:7002\n\n");

    printColor(8, "注意：服务已最小化到后台运行\n");
    printColor(8, "如需停止服务，请运行 stop.exe\n\n");

    printf("按任意键关闭窗口...");
    system("pause > nul");
    return 0;
}