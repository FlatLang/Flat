#ifndef NOVA_NATIVE_CLIENT_SOCKET
#define NOVA_NATIVE_CLIENT_SOCKET

#include "NativeSocket.h"

SOCKET_ID_TYPE nova_clientsocket_connect(char* ipAddress, int port);

#endif
