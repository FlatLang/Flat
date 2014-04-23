#include "Person.h"
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"

PRIVATE
(
	int fathom_age;
	String* fathom_name;
)

Person* fathom_Person_Person(ExceptionData* exceptionData, String* fathom_name_134, int fathom_age_134)
{
	NEW(Person, reference);
	
	reference->prv->fathom_age = 0;
	reference->prv->fathom_name = 0;
	{
		reference->prv->fathom_name = fathom_name_134;
		reference->prv->fathom_age = fathom_age_134;
	}
	
	return reference;
}

void fathom_del_Person(Person** reference, ExceptionData* exceptionData)
{
	if (!*reference)
	{
		return;
	}
	
	fathom_del_String(&(*reference)->prv->fathom_name, exceptionData);
	free((*reference)->prv);
	
	{
	}
	free(*reference);
}

int fathom_Person_getAge(Person* reference, ExceptionData* exceptionData)
{
	return reference->prv->fathom_age;
}

String* fathom_Person_getName(Person* reference, ExceptionData* exceptionData)
{
	return reference->prv->fathom_name;
}
