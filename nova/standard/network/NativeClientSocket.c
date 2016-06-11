#include "NativeClientSocket.h"

SOCKET_ID_TYPE nova_clientsocket_connect(char* ipAddress, int port)
{
#ifdef _WIN32
	WSADATA wsa;
	SOCKET s;
	int result;
	struct sockaddr_in server;

	result = WSAStartup(MAKEWORD(2, 2), &wsa);

	if (result != 0)
	{
		printf("Failed to startup winsock...\n");

		return 0;
	}

	s = socket(AF_INET, SOCK_STREAM, 0);

	if (s == INVALID_SOCKET)
	{
		//printf("Could not create socket: %d\n", WSAGetLastError());

		return 0;
	}

	server.sin_addr.s_addr = inet_addr(ipAddress);
	server.sin_family = AF_INET;
	server.sin_port = htons(port);

	result = connect(s, (struct sockaddr*)&server, sizeof(struct sockaddr_in));

	if (result < 0)
	{
		return 0;
	}

	return s;
#else
	int sockfd = 0;
	int result = 0;
	struct sockaddr_in serv_addr;

	sockfd = socket(AF_INET, SOCK_STREAM, 0);

	if (sockfd < 0)
	{
		return 0;
	}

	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(port);
	serv_addr.sin_addr.s_addr = inet_addr(ipAddress);

	result = connect(sockfd, (struct sockaddr*)&serv_addr, sizeof(struct sockaddr_in));
	
	if (result < 0)
	{
		return 0;
	}

	return sockfd;
#endif
}
