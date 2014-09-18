#pragma once
#ifndef FILE_Stack_NOVA
#define FILE_Stack_NOVA

typedef struct Stack Stack;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaNull.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaListNode.h>
#include <NovaEmptyStackException.h>

typedef struct nova_VTable_Stack
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Stack;

CCLASS_CLASS
(
	Stack, 
	
	nova_VTable_Stack* vtable;
	struct Private* prv;
)

Stack* nova_0_Stack_construct(Stack* this, ExceptionData* exceptionData);
void nova_del_Stack(Stack** this, ExceptionData* exceptionData);
void nova_Stack_push(Stack* this, ExceptionData* exceptionData, Object* nova_0_data);
Object* nova_Stack_pop(Stack* this, ExceptionData* exceptionData);
char nova_Stack_isEmpty(Stack* this, ExceptionData* exceptionData);
void nova_Stack_this(Stack* this, ExceptionData* exceptionData);
void nova_Stack_super(Stack* this, ExceptionData* exceptionData);

#endif