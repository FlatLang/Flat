#pragma once
#ifndef FILE_Object_NOVA
#define FILE_Object_NOVA

typedef struct Object Object;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"

Object* nova_Object_Object(ExceptionData* exceptionData);
void nova_del_Object(Object** this, ExceptionData* exceptionData);
String* nova_Object_toString(Object* this, ExceptionData* exceptionData);

#endif