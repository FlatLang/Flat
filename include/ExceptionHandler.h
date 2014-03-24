#ifndef EXCEPTION_HANDLER_H
#define EXCEPTION_HANDLER_H

#include <stdio.h>
#include <setjmp.h>

#define TRY \
	do\
	{\
		jmp_buf __FATHOM__jmp_buf;\
		\
		switch(setjmp(__FATHOM__jmp_buf))\
		{\
			case 0:\
				while(1)\
				{

#define CATCH(x) \
	break;\
	case x:

#define FINALLY	\
		break;\
	}\
	default:

#define END_TRY \
		}\
	}\
	while(0)

#define THROW(x) longjmp(__FATHOM__jmp_buf, x)

#endif