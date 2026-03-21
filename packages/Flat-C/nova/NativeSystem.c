#include "NativeSystem.h"

void error(int code, char message[], error_func func, void* ref, void* context)
{
	nova_Nova_String* s = nova_Nova_String_1_Nova_construct(0, message);

	func(ref, code, s, 1, context);
}

#ifdef _WIN32
FILE* getPipe(char command[], error_func func, void* ref, void* context)
{
	FILE* pPipe;
	
	char buf[256];
	
	/**
	 * Flags:
	 * "r" The calling process can read the spawned command's standard output using the returned stream.
	 * "w" The calling process can write to the spawned command's standard input using the returned stream.
	 * "b" Open in binary mode.
	 * "t" Open in text mode.
	 */
	pPipe = _popen(command, "rt");

	if (pPipe == 0)
	{
		error(1, "_popen error...", func, ref, context);
	}

	/*while (fgets(buf, 256, pPipe))
	{
		printf(buf);
	}*/

	return pPipe;
}
#else
FILE* getPipe(char command[], error_func func, void* ref, void* context)
{
	pid_t pid = 0;
	int pipefd[2];
	int status;

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
			error(1, "execv error.", func, ref, context);
		}
		
		exit(0);
	}
	else if (pid < 0)
	{
		error(1, "Could not create process.", func, ref, context);
	}
	
	if (waitpid(pid, &status, 0) != pid)
	{
		error(1, "Could not wait for process.", func, ref, context);
	}
	
	close(pipefd[1]);
	
	while (read(pipefd[0], buf, sizeof(buf)) != 0)
	{
		printf("%s\n", buf);
	}

	return 0;//pipefd[0];
}
#endif
