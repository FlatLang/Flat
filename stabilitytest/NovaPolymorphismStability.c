#include <precompiled.h>
#include "NovaPolymorphismStability.h"

typedef String* (*nova_1_0_closure)(void*, ExceptionData*);
typedef String* (*nova_2_0_closure)(void*, ExceptionData*);

nova_VTable_PolymorphismStability nova_VTable_PolymorphismStability_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

void nova_static_PolymorphismStability_testCalls(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
char nova_static_PolymorphismStability_testSubCall(PolymorphismStability* this, ExceptionData* exceptionData, PolymorphicSuperClass* nova_0_obj);
void nova_static_PolymorphismStability_testClosure(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
char nova_static_PolymorphismStability_callPolymorphicClosure(PolymorphismStability* this, ExceptionData* exceptionData, nova_2_0_closure nova_0_closure, void* nova_ref_0_closure);

PolymorphismStability* nova_0_PolymorphismStability_construct(PolymorphismStability* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(PolymorphismStability, this,);
	this->vtable = &nova_VTable_PolymorphismStability_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_PolymorphismStability_super(this, 0);
	
	{
		nova_PolymorphismStability_this(this, exceptionData);
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
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "Checking Polymorphism stability..."));
	nova_static_PolymorphismStability_testCalls((PolymorphismStability*)nova_null, exceptionData, nova_0_program);
	nova_static_PolymorphismStability_testClosure((PolymorphismStability*)nova_null, exceptionData, nova_0_program);
}

void nova_static_PolymorphismStability_testCalls(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	PolymorphicSuperClass* nova_1_obj1;
	PolymorphicSubClass* nova_1_obj2;
	
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Checking polymorphic method calls... "));
	nova_1_obj1 = nova_0_PolymorphicSuperClass_construct(0, exceptionData);
	nova_1_obj2 = nova_0_PolymorphicSubClass_construct(0, exceptionData);
	if (!nova_static_PolymorphismStability_testSubCall((PolymorphismStability*)nova_null, exceptionData, (PolymorphicSuperClass*)(nova_1_obj2)))
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Did not call sub class method"));
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "OK"));
}

char nova_static_PolymorphismStability_testSubCall(PolymorphismStability* this, ExceptionData* exceptionData, PolymorphicSuperClass* nova_0_obj)
{
	String* nova_local_0;
	
	nova_local_0 = nova_0_obj->vtable->nova_virtual_0_toString(nova_0_obj, exceptionData);
	return nova_local_0->vtable->nova_virtual_0_equals(nova_local_0, exceptionData, nova_String_construct(0, exceptionData, "sub class"));
}

void nova_static_PolymorphismStability_testClosure(PolymorphismStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	PolymorphicSuperClass* nova_1_obj;
	String* nova_1_child;
	String* nova_local_0;
	
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Checking polymorphic closure method calls... "));
	nova_1_obj = (PolymorphicSuperClass*)(nova_0_PolymorphicSubClass_construct(0, exceptionData));
	if (!nova_static_PolymorphismStability_callPolymorphicClosure((PolymorphismStability*)nova_null, exceptionData, (nova_2_0_closure)nova_1_obj->vtable->nova_virtual_0_toString, nova_1_obj))
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Did not call sub class method as closure"));
	}
	nova_PolymorphicSuperClass_giveBirth(nova_1_obj, exceptionData);
	nova_local_0 = nova_1_obj->nova_PolymorphicSuperClass_child->vtable->nova_virtual_0_toString(nova_1_obj->nova_PolymorphicSuperClass_child, exceptionData);
	nova_1_child = nova_local_0->vtable->nova_virtual_0_concat(nova_local_0, exceptionData, nova_String_construct(0, exceptionData, "!"));
	if (!nova_static_PolymorphismStability_callPolymorphicClosure((PolymorphismStability*)nova_null, exceptionData, (nova_2_0_closure)nova_1_obj->nova_PolymorphicSuperClass_child->vtable->nova_virtual_0_toString, nova_1_obj->nova_PolymorphicSuperClass_child))
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Did not call sub class method as closure"));
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "OK"));
}

char nova_static_PolymorphismStability_callPolymorphicClosure(PolymorphismStability* this, ExceptionData* exceptionData, nova_2_0_closure nova_0_closure, void* nova_ref_0_closure)
{
	String* nova_local_0;
	
	nova_local_0 = nova_0_closure(nova_ref_0_closure, exceptionData);
	return nova_local_0->vtable->nova_virtual_0_equals(nova_local_0, exceptionData, nova_String_construct(0, exceptionData, "sub class"));
}

void nova_PolymorphismStability_this(PolymorphismStability* this, ExceptionData* exceptionData)
{
}

void nova_PolymorphismStability_super(PolymorphismStability* this, ExceptionData* exceptionData)
{
}
