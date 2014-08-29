#pragma once
#ifndef FILE_NonWholeDivisionException_NOVA
#define FILE_NonWholeDivisionException_NOVA

typedef struct NonWholeDivisionException NonWholeDivisionException;

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



NonWholeDivisionException* nova_NonWholeDivisionException_construct(NonWholeDivisionException* this, ExceptionData* exceptionData);
void nova_del_NonWholeDivisionException(NonWholeDivisionException** this, ExceptionData* exceptionData);
void nova_NonWholeDivisionException_this(NonWholeDivisionException* this, ExceptionData* exceptionData);
void nova_NonWholeDivisionException_super(NonWholeDivisionException* this, ExceptionData* exceptionData);

#endif