#ifndef FILE_Integer_NOVA
#define FILE_Integer_NOVA

typedef struct Integer Integer;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"

CCLASS_CLASS
(
	Integer, 
	
	int nova_Integer_value;
)


Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_Integer_value_74);
void nova_del_Integer(Integer** this, ExceptionData* exceptionData);
int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_Integer_number_90);
String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_Integer_value_158);
String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData);
#endif