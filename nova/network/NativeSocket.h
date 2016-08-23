#ifndef NOVA_NATIVE_SOCKET
#define NOVA_NATIVE_SOCKET

#include <MacroLib.h>
#include <stdlib.h>

#ifdef _WIN32
#	include <io.h>
#	include <stdio.h>
#	include <winsock2.h>

#	define SOCKET_ID_TYPE SOCKET
#else
#	include <sys/socket.h>
#	include <sys/types.h>
#	include <netinet/in.h>
#	include <netdb.h>
#	include <stdio.h>
#	include <string.h>
#	include <unistd.h>
#	include <errno.h>
#	include <arpa/inet.h>

#	define SOCKET_ID_TYPE int
#endif

char nova_socket_send(SOCKET_ID_TYPE sendTo, char* message);
char* nova_socket_receive(SOCKET_ID_TYPE socket);
char nova_socket_close(SOCKET_ID_TYPE s);

#endif
