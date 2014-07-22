#pragma once
#ifndef FILE_PolymorphismDemo_NOVA
#define FILE_PolymorphismDemo_NOVA

typedef struct PolymorphismDemo PolymorphismDemo;

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
#include <NovaAnimal.h>
#include <NovaSpider.h>
#include <NovaDog.h>



PolymorphismDemo* nova_PolymorphismDemo_PolymorphismDemo(ExceptionData* exceptionData);
void nova_del_PolymorphismDemo(PolymorphismDemo** this, ExceptionData* exceptionData);
void nova_static_PolymorphismDemo_main(PolymorphismDemo* this, ExceptionData* exceptionData, String** nova_0_args);

#endif