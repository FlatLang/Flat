#ifndef NOVA_NATIVE_SERVER_SOCKET
#define NOVA_NATIVE_SERVER_SOCKET

#include "NativeSocket.h"

SOCKET_ID_TYPE nova_serversocket_start(int port);
SOCKET_ID_TYPE nova_serversocket_accept(SOCKET_ID_TYPE s);

#endif
