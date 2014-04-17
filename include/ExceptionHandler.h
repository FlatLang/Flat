#ifndef EXCEPTION_HANDLER_H
#define EXCEPTION_HANDLER_H

#include <stdio.h>
#include <setjmp.h>

#define TRY \
	do\
	{\
		jmp_buf __FATHOM__jmp_buf;\
		\
		ExceptionData* newData = new_ExceptionData(__FATHOM__exception_data, &__FATHOM__jmp_buf);\
		\
		if (__FATHOM__exception_data)\
		{\
			newData->setParent(newData, __FATHOM__exception_data, __FATHOM__exception_data);\
		}\
		\
		__FATHOM__exception_data = newData;\
		\
		int __FATHOM__exception_code = setjmp(__FATHOM__jmp_buf);\
		\
		if (__FATHOM__exception_code == 0)

#define CATCH(x) \
	else if (__FATHOM__exception_code == x)

#define FINALLY	\
	

#define END_TRY \
		{\
			ExceptionData* oldData = __FATHOM__exception_data;\
			ExceptionData* newData = __FATHOM__exception_data->getParent(__FATHOM__exception_data, 0);\
			if (newData != 0)\
			{\
				__FATHOM__exception_data = newData;\
			}\
			/*printf("Bef%p\n", oldData);*/\
			if (oldData != 0)\
			{\
				free(oldData);\
				/*del_ExceptionData(oldData, oldData);*/\
			}\
			/*printf("Aft%p\n", oldData);*/\
		}\
	}\
	while(0)

#define THROW(x) __FATHOM__exception_data->jumpToBuffer(__FATHOM__exception_data, 0, x);

#endif
