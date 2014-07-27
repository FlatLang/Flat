#include "NativeSystem.h"

FILE* getPipe(char command[])
{
	pid_t pid = NULL;
	int pipefd[2];
	int status;
	
	FILE* output;
	char buf[256];
	
	pipe(pipefd);
	pid = fork();
	
	if (pid == 0)
	{
		close(pipefd[0]);
		
		//dup2(pipefd[1], 1);
		//dup2(pipefd[1], 2);
		
		//dup2(pipefd[0], STDIN_FILENO);
		dup2(pipefd[1], STDOUT_FILENO);
		dup2(pipefd[1], STDERR_FILENO);
		
		char* const argv[] = { command, 0 };
		
		if (execv(command, argv) < 0)
		{
			printf("Failed\n");
			fputs("execv error....", stderr);
			
			exit(1);
		}
		
		printf("Success!\n");
		
		exit(0);
	}
	else if (pid < 0)
	{
		fputs("Couldn't create process...", stderr);
	}
	
	if (waitpid(pid, &status, 0) != pid)
	{
		fputs("Couldn't wait for process...", stderr);
	}
	
	close(pipefd[1]);
	
	while (read(pipefd[0], buf, sizeof(buf)) != 0)
	{
		printf("%s\n", buf);
	}
}