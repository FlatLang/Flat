#pragma once
#ifndef FILE_EmptyStackException_NOVA
#define FILE_EmptyStackException_NOVA

typedef struct EmptyStackException EmptyStackException;

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



EmptyStackException* nova_0_EmptyStackException_construct(EmptyStackException* this, ExceptionData* exceptionData);
void nova_del_EmptyStackException(EmptyStackException** this, ExceptionData* exceptionData);
void nova_EmptyStackException_this(EmptyStackException* this, ExceptionData* exceptionData);
void nova_EmptyStackException_super(EmptyStackException* this, ExceptionData* exceptionData);

#endif