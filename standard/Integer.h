#ifndef FILE_Integer_NOVA
#define FILE_Integer_NOVA

typedef struct Integer Integer;

#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"

CCLASS_CLASS
(
	Integer, 
	
	int nova_Integer_value;
)

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_0_value);
void nova_del_Integer(Integer** this, ExceptionData* exceptionData);
int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_0_number);
String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_0_value);
String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData);

#endif