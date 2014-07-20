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
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

CCLASS_CLASS
(
	BodyBuilder, 
	
	int nova_Person_age;
	String* nova_Person_name;
	int nova_BodyBuilder_weightClass;
)

BodyBuilder* nova_BodyBuilder_BodyBuilder(ExceptionData* exceptionData, int nova_0_weightClass, String* nova_0_name);
void nova_del_BodyBuilder(BodyBuilder** this, ExceptionData* exceptionData);
void nova_BodyBuilder_sayHello(BodyBuilder* this, ExceptionData* exceptionData);

#endif