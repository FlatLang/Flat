#include "NativeSocket.h"

int bufSize = 128;

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

	//if (len >= bufSize)
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
	
	if (result == SOCKET_ERROR)
#else
	result = write(sendTo, message, strlen(message));
	
	if (result < 0)
#endif
	{
		//printf("Failed to send data... %d\n", WSAGetLastError());

		return 0;
	}

	return 1;
}

char* nova_socket_receive(SOCKET_ID_TYPE socket)
{
	int length, messageLength, count, total, offset, size;
	char* message;

	offset = 0;
	total = 0;
	messageLength = 1;
	count = 0;

	message = NOVA_MALLOC(sizeof(char) * messageLength);
	message[0] = '\0';

	do
	{
		messageLength += bufSize;

		message = NOVA_REALLOC(message, sizeof(char) * messageLength);

#ifdef _WIN32
		length = recv(socket, &message[messageLength - bufSize - 1], bufSize, 0);

		if (length == SOCKET_ERROR)
#else
		length = read(socket, &message[messageLength - bufSize - 1], bufSize);
		
		if (length <= 0)
#endif
		{
			return 0;
		}

		if (count++ == 0)// && length >= bufSize)
		{
//			if (message[0] == 'c')
//			{
//				NOVA_FREE(message);
//				
//				return 0;
//			}
			
			total = atoi(message);

			for (offset = 1; message[offset - 1] != ' '; offset++);
		}
		
		/*char buffer[bufSize + 1];
		
#ifdef _WIN32
		length = recv(socket, buffer, bufSize, 0);

		if (length == SOCKET_ERROR)
#else
		length = read(socket, buffer, bufSize);
		
		if (length <= 0)
#endif
		{
			return 0;
		}
		
		buffer[length] = '\0';
		
		messageLength += length;

		message = NOVA_REALLOC(message, sizeof(char) * messageLength);
		
		if (count++ == 0)
		{
			if (length >= 128)
			{
				total = atoi(message);
	
				for (offset = 1; message[offset - 1] != ' '; offset++);
			}
			
			strcpy(message, &buffer[offset]);
			strcat(message, "\n");
			message[length + offset] = '\0';
			
			printf("%s!\n", message);
		}
		else
		{
			strcat(message, buffer);
		}*/
	}
	while (length >= bufSize && messageLength - offset - 1 != total);

	size = messageLength - (bufSize - length);
	
	message[size - 1] = '\0';//strlen(message)] = '\0';
	
	message = NOVA_REALLOC(message, sizeof(char) * size);
	
	return &message[offset];
}

char nova_socket_close(SOCKET_ID_TYPE s)
{
//	char* closeMessage = "c aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//	
//	#ifdef _WIN32
//		send(s, closeMessage, strlen(closeMessage), 0);
//	#else
//		write(s, closeMessage, strlen(closeMessage));
//	#endif
	
#ifdef _WIN32
	closesocket(s);
	WSACleanup();
#else
	close(s);
#endif

	return 1;
}
