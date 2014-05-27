#ifndef FILE_BodyBuilder_NOVA
#define FILE_BodyBuilder_NOVA

typedef struct BodyBuilder BodyBuilder;

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
#include "IO.h"

CCLASS_CLASS
(
	BodyBuilder, 
	
	int nova_Person_age;
	String* nova_Person_name;
	int nova_BodyBuilder_weightClass;
)


BodyBuilder* nova_BodyBuilder_BodyBuilder(ExceptionData* exceptionData, int nova_BodyBuilder_weightClass_62, String* nova_BodyBuilder_name_62);
void nova_del_BodyBuilder(BodyBuilder** this, ExceptionData* exceptionData);
void nova_BodyBuilder_sayHello(BodyBuilder* this, ExceptionData* exceptionData);
#endif