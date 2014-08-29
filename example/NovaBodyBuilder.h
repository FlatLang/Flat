#pragma once
#ifndef FILE_BodyBuilder_NOVA
#define FILE_BodyBuilder_NOVA

typedef struct BodyBuilder BodyBuilder;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
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
#include <NovaPerson.h>

typedef struct nova_VTable_BodyBuilder
{
	void (*nova_virtual_0_sayHello)(BodyBuilder*, ExceptionData*);
} nova_VTable_BodyBuilder;

CCLASS_CLASS
(
	BodyBuilder, 
	
	nova_VTable_BodyBuilder* vtable;
	int nova_Person_age;
	String* nova_Person_name;
	int nova_BodyBuilder_weightClass;
)

BodyBuilder* nova_BodyBuilder_construct(BodyBuilder* this, ExceptionData* exceptionData, int nova_0_weightClass, String* nova_0_name);
void nova_del_BodyBuilder(BodyBuilder** this, ExceptionData* exceptionData);
void nova_BodyBuilder_this(BodyBuilder* this, ExceptionData* exceptionData, int nova_0_weightClass, String* nova_0_name);
void nova_0_BodyBuilder_sayHello(BodyBuilder* this, ExceptionData* exceptionData);
void nova_BodyBuilder_super(BodyBuilder* this, ExceptionData* exceptionData);

#endif