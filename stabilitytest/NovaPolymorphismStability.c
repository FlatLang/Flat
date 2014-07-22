#include <precompiled.h>
#include "NovaPolymorphismStability.h"

typedef String* (*nova_1_0_closure)(void*, ExceptionData*);



void nova_static_PolymorphismStability_testCalls(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
char nova_static_PolymorphismStability_testSubCall(PolymorphismStability* this, ExceptionData* exceptionData, PolymorphicSuperClass* nova_0_obj);
void nova_static_PolymorphismStability_testClosure(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
char nova_static_PolymorphismStability_callPolymorphicClosure(PolymorphismStability* this, ExceptionData* exceptionData, nova_1_0_closure nova_0_closure, void* nova_ref_0_closure);

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
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "Checking Polymorphism stability..."));
	nova_static_PolymorphismStability_testCalls((PolymorphismStability*)0, exceptionData, nova_0_program);
	nova_static_PolymorphismStability_testClosure((PolymorphismStability*)0, exceptionData, nova_0_program);
}

void nova_static_PolymorphismStability_testCalls(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	PolymorphicSuperClass* nova_1_obj1;
	PolymorphicSubClass* nova_1_obj2;
	
	nova_static_Console_write((Console*)(0), exceptionData, nova_String_String(exceptionData, "Checking polymorphic method calls... "));
	nova_1_obj1 = nova_PolymorphicSuperClass_PolymorphicSuperClass(exceptionData);
	nova_1_obj2 = nova_PolymorphicSubClass_PolymorphicSubClass(exceptionData);
	if (!nova_static_PolymorphismStability_testSubCall((PolymorphismStability*)0, exceptionData, (PolymorphicSuperClass*)(nova_1_obj2)))
	{
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Did not call sub class method"));
	}
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "OK"));
}

char nova_static_PolymorphismStability_testSubCall(PolymorphismStability* this, ExceptionData* exceptionData, PolymorphicSuperClass* nova_0_obj)
{
	String* nova_local_0;
	
	nova_local_0 = nova_0_obj->vtable->nova_virtual_toString((PolymorphicSuperClass*)(nova_0_obj), exceptionData);
	return nova_local_0->vtable->nova_virtual_equals((String*)(nova_local_0), exceptionData, nova_String_String(exceptionData, "sub class"));
}

void nova_static_PolymorphismStability_testClosure(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	PolymorphicSuperClass* nova_1_obj;
	String* nova_1_child;
	
	nova_static_Console_write((Console*)(0), exceptionData, nova_String_String(exceptionData, "Checking polymorphic closure method calls... "));
	nova_1_obj = (PolymorphicSuperClass*)(nova_PolymorphicSubClass_PolymorphicSubClass(exceptionData));
	if (!nova_static_PolymorphismStability_callPolymorphicClosure((PolymorphismStability*)0, exceptionData, (nova_1_0_closure)nova_1_obj->vtable->nova_virtual_toString, nova_1_obj))
	{
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Did not call sub class method as closure"));
	}
	nova_PolymorphicSuperClass_giveBirth((PolymorphicSuperClass*)(nova_1_obj), exceptionData);
	nova_1_child = nova_String_concat(nova_1_obj->nova_PolymorphicSuperClass_child->vtable->nova_virtual_toString((PolymorphicSubClass*)(nova_1_obj->nova_PolymorphicSuperClass_child), exceptionData), exceptionData, nova_String_String(exceptionData, (char*)("!")));
	if (!nova_static_PolymorphismStability_callPolymorphicClosure((PolymorphismStability*)0, exceptionData, (nova_1_0_closure)nova_1_obj->nova_PolymorphicSuperClass_child->vtable->nova_virtual_toString, nova_1_obj->nova_PolymorphicSuperClass_child))
	{
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Did not call sub class method as closure"));
	}
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "OK"));
}

char nova_static_PolymorphismStability_callPolymorphicClosure(PolymorphismStability* this, ExceptionData* exceptionData, nova_1_0_closure nova_0_closure, void* nova_ref_0_closure)
{
	String* nova_local_0;
	
	nova_local_0 = nova_0_closure(nova_ref_0_closure, exceptionData);
	return nova_local_0->vtable->nova_virtual_equals((String*)(nova_local_0), exceptionData, nova_String_String(exceptionData, "sub class"));
}
