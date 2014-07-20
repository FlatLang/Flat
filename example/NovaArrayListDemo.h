#pragma once
#ifndef FILE_ArrayListDemo_NOVA
#define FILE_ArrayListDemo_NOVA

typedef struct ArrayListDemo ArrayListDemo;

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
#include <NovaArrayList.h>

ArrayListDemo* nova_ArrayListDemo_ArrayListDemo(ExceptionData* exceptionData);
void nova_del_ArrayListDemo(ArrayListDemo** this, ExceptionData* exceptionData);
void nova_static_ArrayListDemo_main(ArrayListDemo* this, ExceptionData* exceptionData, String** nova_0_args);

#endif