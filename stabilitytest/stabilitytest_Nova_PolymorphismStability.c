#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_PolymorphismStability.h>

typedef nova_standard_Nova_String* (*l0_1_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*);
typedef nova_standard_Nova_String* (*l0_2_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*);
typedef nova_standard_Nova_String* (*l0_3_Nova_closure)(void*, nova_standard_exception_Nova_ExceptionData*);

stabilitytest_Extension_VTable_PolymorphismStability stabilitytest_Extension_VTable_PolymorphismStability_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



void stabilitytest_Nova_PolymorphismStability_Nova_testCalls(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
char stabilitytest_Nova_PolymorphismStability_Nova_testSubCall(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_PolymorphicSuperClass* l0_Nova_obj);
void stabilitytest_Nova_PolymorphismStability_Nova_testClosure(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
char stabilitytest_Nova_PolymorphismStability_Nova_callPolymorphicClosure(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_3_Nova_closure l0_Nova_closure, void* l0_ref_Nova_closure);
void stabilitytest_Nova_PolymorphismStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_PolymorphismStability* stabilitytest_Nova_PolymorphismStability_2_Nova_construct(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_Nova_PolymorphismStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_PolymorphismStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	stabilitytest_Nova_PolymorphismStability_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_PolymorphismStability_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void stabilitytest_Nova_PolymorphismStability_Nova_destroy(stabilitytest_Nova_PolymorphismStability** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_PolymorphismStability_Nova_test(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking Polymorphism stability..."));
	stabilitytest_Nova_PolymorphismStability_Nova_testCalls((stabilitytest_Nova_PolymorphismStability*)nova_null, exceptionData, l0_Nova_program);
	stabilitytest_Nova_PolymorphismStability_Nova_testClosure((stabilitytest_Nova_PolymorphismStability*)nova_null, exceptionData, l0_Nova_program);
}

void stabilitytest_Nova_PolymorphismStability_Nova_testCalls(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	stabilitytest_Nova_PolymorphicSuperClass* l1_Nova_obj1;
	stabilitytest_Nova_PolymorphicSubClass* l1_Nova_obj2;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking polymorphic method calls... "));
	l1_Nova_obj1 = stabilitytest_Nova_PolymorphicSuperClass_2_Nova_construct(0, exceptionData);
	l1_Nova_obj2 = stabilitytest_Nova_PolymorphicSubClass_2_Nova_construct(0, exceptionData);
	if (!stabilitytest_Nova_PolymorphismStability_Nova_testSubCall((stabilitytest_Nova_PolymorphismStability*)nova_null, exceptionData, (stabilitytest_Nova_PolymorphicSuperClass*)(l1_Nova_obj2)))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Did not call sub class method"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

char stabilitytest_Nova_PolymorphismStability_Nova_testSubCall(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_PolymorphicSuperClass* l0_Nova_obj)
{
	nova_standard_Nova_String* nova_local_0;
	
	nova_local_0 = l0_Nova_obj->vtable->stabilitytest_Nova_PolymorphicSuperClass_virtual0_Nova_toString(l0_Nova_obj, exceptionData);
	return nova_local_0->vtable->nova_standard_Nova_String_virtual_Nova_equals(nova_local_0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "sub class"));
}

void stabilitytest_Nova_PolymorphismStability_Nova_testClosure(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	stabilitytest_Nova_PolymorphicSuperClass* l1_Nova_obj;
	nova_standard_Nova_String* l1_Nova_child;
	nova_standard_Nova_String* nova_local_0;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking polymorphic closure method calls... "));
	l1_Nova_obj = (stabilitytest_Nova_PolymorphicSuperClass*)(stabilitytest_Nova_PolymorphicSubClass_2_Nova_construct(0, exceptionData));
	if (!stabilitytest_Nova_PolymorphismStability_Nova_callPolymorphicClosure((stabilitytest_Nova_PolymorphismStability*)nova_null, exceptionData, (l0_3_Nova_closure)l1_Nova_obj->vtable->stabilitytest_Nova_PolymorphicSuperClass_virtual0_Nova_toString, l1_Nova_obj))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Did not call sub class method as closure"));
	}
	stabilitytest_Nova_PolymorphicSuperClass_Nova_giveBirth(l1_Nova_obj, exceptionData);
	nova_local_0 = l1_Nova_obj->stabilitytest_Nova_PolymorphicSuperClass_Nova_child->vtable->stabilitytest_Nova_PolymorphicSubClass_virtual0_Nova_toString(l1_Nova_obj->stabilitytest_Nova_PolymorphicSuperClass_Nova_child, exceptionData);
	l1_Nova_child = nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "!"));
	if (!stabilitytest_Nova_PolymorphismStability_Nova_callPolymorphicClosure((stabilitytest_Nova_PolymorphismStability*)nova_null, exceptionData, (l0_3_Nova_closure)l1_Nova_obj->stabilitytest_Nova_PolymorphicSuperClass_Nova_child->vtable->stabilitytest_Nova_PolymorphicSubClass_virtual0_Nova_toString, l1_Nova_obj->stabilitytest_Nova_PolymorphicSuperClass_Nova_child))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Did not call sub class method as closure"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

char stabilitytest_Nova_PolymorphismStability_Nova_callPolymorphicClosure(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, l0_3_Nova_closure l0_Nova_closure, void* l0_ref_Nova_closure)
{
	nova_standard_Nova_String* nova_local_0;
	
	nova_local_0 = l0_Nova_closure(l0_ref_Nova_closure, exceptionData);
	return nova_local_0->vtable->nova_standard_Nova_String_virtual_Nova_equals(nova_local_0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "sub class"));
}

void stabilitytest_Nova_PolymorphismStability_2_Nova_this(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void stabilitytest_Nova_PolymorphismStability_Nova_super(stabilitytest_Nova_PolymorphismStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

