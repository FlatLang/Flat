#ifndef EXCEPTION_HANDLER_H
#define EXCEPTION_HANDLER_H

#include <stdio.h>
#include <setjmp.h>

#define buffer jmp_buf
#define setJump setjmp
#define jump longjmp

#define TRY \
	do\
	{\
		buffer buf;\
		int exception_code;\
		\
		ExceptionData* newData = nova_ExceptionData_ExceptionData(exceptionData, &buf);\
		\
		if (exceptionData != 0)\
		{\
			nova_ExceptionData_setParent(newData, exceptionData, exceptionData);\
		}\
		\
		exceptionData = newData;\
		\
		exception_code = setJump(buf);\
		\
		if (exception_code == 0)

#define CATCH(x) \
	else if (exception_code == x)

#define FINALLY	\
	

#define END_TRY \
		{\
			ExceptionData* oldData = exceptionData;\
			ExceptionData* newData = nova_ExceptionData_getParent(exceptionData, 0);\
			if (newData != 0)\
			{\
				exceptionData = newData;\
			}\
			/*printf("Bef%p\n", oldData);*/\
			if (oldData != 0)\
			{\
				NOVA_FREE(oldData);\
				/*del_ExceptionData(oldData, oldData);*/\
			}\
			/*printf("Aft%p\n", oldData);*/\
		}\
	}\
	while(0)

#define THROW(x) nova_ExceptionData_jumpToBuffer(exceptionData, 0, x);

#endif
