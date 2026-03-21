#ifndef EXCEPTION_HANDLER_H
#define EXCEPTION_HANDLER_H

#include <stdio.h>
#include <setjmp.h>

#define buffer jmp_buf
#define setJump setjmp
#define jump longjmp

#define TRY \
	{\
		buffer* buf = NOVA_MALLOC(sizeof(buffer));\
		int exception_code;\
		nova_exception_Nova_ExceptionData* thrownData;\
		\
		nova_exception_Nova_ExceptionData* tryContextData = nova_exception_Nova_ExceptionData_Nova_construct(0, buf);\
		\
		if (exceptionData != 0) {\
			tryContextData->nova_exception_Nova_ExceptionData_Nova_parent = exceptionData;\
		}\
		\
		exceptionData  = tryContextData;\
		exception_code = setJump(*buf);\
		\
		if (exception_code != 0) {\
			thrownData = exceptionData;\
			exceptionData = exceptionData->nova_exception_Nova_ExceptionData_Nova_parent;\
		}\
		\
		if (exception_code == 0)

#define CATCH(x)\
	else if (nova_meta_Nova_Class_Nova_isOfType(thrownData->nova_exception_Nova_ExceptionData_Nova_thrownException->vtable->classInstance, x))

#define FINALLY

#define END_TRY \
		{\
		}\
	}

#define THROW(exception, soft) nova_exception_Nova_ExceptionData_Nova_throwException(exceptionData, &exceptionData, (nova_exception_Nova_Exception*)exception, soft)

#endif