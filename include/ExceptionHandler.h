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
		nova_standard_exception_Nova_ExceptionData* newData = novaEnv.nova_standard_exception_ExceptionData.construct(0, exceptionData, &buf);\
		\
		if (exceptionData != 0)\
		{\
			novaEnv.nova_standard_exception_ExceptionData.setParent(newData, exceptionData, exceptionData);\
		}\
		\
		exceptionData  = newData;\
		exception_code = setJump(buf);\
		\
		if (exception_code == 0)

#define CATCH(x)\
	else if (exception_code == x)

#define FINALLY

#define END_TRY \
		{\
			nova_standard_exception_Nova_ExceptionData* oldData = exceptionData;\
			nova_standard_exception_Nova_ExceptionData* newData = novaEnv.nova_standard_exception_ExceptionData.getParent(exceptionData, 0);\
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

#define THROW(x, exception) \
	exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException = (nova_standard_exception_Nova_Exception*)exception;\
	novaEnv.nova_standard_exception_ExceptionData.jumpToBuffer(exceptionData, 0, x);

#endif
