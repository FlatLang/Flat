#pragma once
#ifndef FILE_CharArray_NOVA
#define FILE_CharArray_NOVA

typedef struct CharArray CharArray;

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
	CharArray, 
	
	int nova_Array_length;
)

CharArray* nova_CharArray_CharArray(ExceptionData* exceptionData);
void nova_del_CharArray(CharArray** this, ExceptionData* exceptionData);

#endif