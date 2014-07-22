#include <precompiled.h>
#include "NovaPolymorphismStability.h"




void nova_static_PolymorphismStability_testCalls(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
char nova_static_PolymorphismStability_testSubCall(PolymorphismStability* this, ExceptionData* exceptionData, PolymorphicSuperClass* nova_0_obj);

PolymorphismStability* nova_PolymorphismStability_PolymorphismStability(ExceptionData* exceptionData)
{
	PolymorphismStability* this = (PolymorphismStability*)1;
	
	{
	}
	
	return this;
}

void nova_del_PolymorphismStability(PolymorphismStability** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_PolymorphismStability_test(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "Checking Polymorphism stability..."));
	nova_static_PolymorphismStability_testCalls((Object*)0, exceptionData, nova_0_program);
}

void nova_static_PolymorphismStability_testCalls(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	PolymorphicSuperClass* nova_1_obj1;
	PolymorphicSubClass* nova_1_obj2;
	
	nova_static_Console_write((Object*)0, exceptionData, nova_String_String(exceptionData, "Checking polymorphic method calls... "));
	nova_1_obj1 = nova_PolymorphicSuperClass_PolymorphicSuperClass(exceptionData);
	nova_1_obj2 = nova_PolymorphicSubClass_PolymorphicSubClass(exceptionData);
	if (!nova_static_PolymorphismStability_testSubCall((Object*)0, exceptionData, nova_1_obj2))
	{
		nova_StabilityTest_fail(nova_0_program, exceptionData, nova_String_String(exceptionData, "Did not call sub class method"));
	}
	nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "OK"));
}

char nova_static_PolymorphismStability_testSubCall(PolymorphismStability* this, ExceptionData* exceptionData, PolymorphicSuperClass* nova_0_obj)
{
	String* nova_local_0;
	
	nova_local_0 = nova_0_obj->vtable->nova_virtual_toString(nova_0_obj, exceptionData);
	return nova_local_0->vtable->nova_virtual_equals(nova_local_0, exceptionData, nova_String_String(exceptionData, "sub class"));
}
