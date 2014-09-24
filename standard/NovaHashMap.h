#pragma once
#ifndef FILE_HashMap_NOVA
#define FILE_HashMap_NOVA

typedef struct HashMap HashMap;

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
#include <HashMap.h>

typedef struct nova_VTable_HashMap
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_HashMap;

CCLASS_CLASS
(
	HashMap, 
	
	nova_VTable_HashMap* vtable;
	struct Private* prv;
)

HashMap* nova_0_HashMap_construct(HashMap* this, ExceptionData* exceptionData);
void nova_del_HashMap(HashMap** this, ExceptionData* exceptionData);
void nova_HashMap_this(HashMap* this, ExceptionData* exceptionData);
void nova_HashMap_put(HashMap* this, ExceptionData* exceptionData, Object* nova_0_key, Object* nova_0_value);
Object* nova_HashMap_get(HashMap* this, ExceptionData* exceptionData, Object* nova_0_key);
void nova_HashMap_super(HashMap* this, ExceptionData* exceptionData);

#endif