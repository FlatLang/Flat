#include "NativeServerSocket.h"

SOCKET_ID_TYPE nova_serversocket_start(int port)
{
#ifdef _WIN32
	int result, bindRes, size;
	WSADATA wsa;
	SOCKET s, new_socket;
	struct sockaddr_in* server;

	result = WSAStartup(MAKEWORD(2,2), &wsa);

	if (result != 0)
	{
		return 0;
	}

	s = socket(AF_INET, SOCK_STREAM, 0);

	if (s == INVALID_SOCKET)
	{
		printf("Could not create socket: %d\n", WSAGetLastError());
	}

	size = sizeof(struct sockaddr_in);

	server = malloc(size);

	server->sin_family = AF_INET;
	server->sin_addr.s_addr = INADDR_ANY;
	server->sin_port = htons(port);

	bindRes = bind(s, (struct sockaddr*)server, size);

	if (bindRes == SOCKET_ERROR)
	{
		printf("Bind failed with error code: %d\n", WSAGetLastError());

		exit(EXIT_FAILURE);
	}

	listen(s, 3);

	return s;
#else
	int listenfd = 0;
	int result = 0;

	struct sockaddr_in serv_addr;

	listenfd = socket(AF_INET, SOCK_STREAM, 0);
	
	if (listenfd < 0)
	{
		return 0;
	}

	memset(&serv_addr, '0', sizeof(serv_addr));

	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	serv_addr.sin_port = htons(port);
	
	errno = 0;

	bind(listenfd, (struct sockaddr*)&serv_addr, sizeof(struct sockaddr_in));
	
	if (errno != 0)
	{
		return 0;
	}
	
	result = listen(listenfd, 10);
	
	if (result < 0)
	{
		return 0;
	}

	return listenfd;
#endif
}

SOCKET_ID_TYPE nova_serversocket_accept(SOCKET_ID_TYPE s)
{
#ifdef _WIN32
	struct sockaddr_in client;
	int size;
	SOCKET request;

	size = sizeof(struct sockaddr_in);

	request = accept(s, (struct sockaddr*)&client, &size);

	if (request == INVALID_SOCKET)
	{
		return 0;
	}

	return request;
#else
	int connfd = 0;
	
	connfd = accept(s, (struct sockaddr*)0, 0);

	if (connfd < 0)
	{
		return 0;
	}

	return connfd;
#endif
}
