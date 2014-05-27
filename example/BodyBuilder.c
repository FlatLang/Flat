#include "BodyBuilder.h"
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

BodyBuilder* nova_BodyBuilder_BodyBuilder(ExceptionData* exceptionData, int nova_BodyBuilder_weightClass_62, String* nova_BodyBuilder_name_62)
{
	CCLASS_NEW(BodyBuilder, this,);
	
	this->nova_BodyBuilder_weightClass = 0;
	{
		this->nova_Person_age = 5;
		this->nova_BodyBuilder_weightClass = nova_BodyBuilder_weightClass_62;
		this->nova_Person_name = nova_BodyBuilder_name_62;
	}
	
	return this;
}

void nova_del_BodyBuilder(BodyBuilder** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}

void nova_BodyBuilder_sayHello(BodyBuilder* this, ExceptionData* exceptionData)
{
	nova_IO_println(exceptionData, nova_String_concat(nova_String_String(exceptionData, "Hello from "), exceptionData, nova_String_concat(this->nova_Person_name, exceptionData, nova_String_String(exceptionData, " the BodyBuilder"))));
}
