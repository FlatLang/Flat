#include "NativeSocket.h"

int numDigits(int number)
{
	int size;

	number = number / 10;

	for (size = 1; number > 0; size++)
	{
		number = number / 10;
	}

	return size;
}

char nova_socket_send(SOCKET_ID_TYPE sendTo, char* message)
{
	int result;
	int len, digits;

	len = strlen(message);

	if (len >= 128)
	{
		char* new;
		int digits;

		digits = numDigits(len);

		new = NOVA_MALLOC(sizeof(char) * (len + digits + 2));

		sprintf(new, "%d ", len);

		memcpy(new + digits + 1, message, sizeof(char) * len);

		message = new;

		message[len + digits + 1] = '\0';
	}

#ifdef _WIN32
	result = send(sendTo, message, strlen(message), 0);
#else
	result = write(sendTo, message, strlen(message));
#endif

	if (result == SOCKET_ERROR)
	{
		//printf("Failed to send data... %d\n", WSAGetLastError());

		return 0;
	}

	return 1;
}

char* nova_socket_receive(SOCKET_ID_TYPE socket)
{
	int length, messageLength, count, total, offset;
	char* message;

	offset = 0;
	total = 0;
	messageLength = 1;
	count = 0;

	message = NOVA_MALLOC(sizeof(char) * messageLength);

	do
	{
		messageLength += 128;

		message = NOVA_REALLOC(message, sizeof(char) * messageLength);

#ifdef _WIN32
		length = recv(socket, &message[messageLength - 128 - 1], 128, 0);

		if (length == SOCKET_ERROR)
#else
		length = read(socket, &message[messageLength - 128 - 1], 128);

		if (length < 0)
#endif
		{
			return 0;
		}

		if (count++ == 0 && length >= 128)
		{
			total = atoi(message);

			for (offset = 1; message[offset - 1] != ' '; offset++);
		}
	}
	while (length >= 128 && messageLength - offset - 1 != total);

	message[messageLength - (128 - length) - 1] = '\0';

	return &message[offset];
}

char nova_socket_close(SOCKET_ID_TYPE s)
{
#ifdef _WIN32
	closesocket(s);
	WSACleanup();
#else
	close(s);
#endif

	return 1;
}
