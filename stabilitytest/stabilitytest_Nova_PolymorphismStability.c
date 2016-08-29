#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_PolymorphismStability.h>


typedef struct nova_exception_Nova_ExceptionData nova_exception_Nova_ExceptionData;
typedef struct nova_Nova_String nova_Nova_String;

typedef nova_Nova_String* (*stabilitytest_Nova_PolymorphismStability_closure1_Nova_closure)(void*, nova_exception_Nova_ExceptionData*, void*);
typedef nova_Nova_String* (*stabilitytest_Nova_PolymorphismStability_closure2_Nova_closure)(void*, nova_exception_Nova_ExceptionData*, void*);
typedef nova_Nova_String* (*stabilitytest_Nova_PolymorphismStability_closure3_Nova_closure)(void*, nova_exception_Nova_ExceptionData*, void*);

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
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	stabilitytest_Nova_PolymorphismStability_0_Nova_test,
};



void stabilitytest_Nova_PolymorphismStability_Nova_testCalls(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData);
char stabilitytest_Nova_PolymorphismStability_Nova_testSubCall(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_PolymorphicSuperClass* stabilitytest_Nova_PolymorphismStability_Nova_obj);
void stabilitytest_Nova_PolymorphismStability_Nova_testClosure(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData);
char stabilitytest_Nova_PolymorphismStability_Nova_callPolymorphicClosure(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_PolymorphismStability_closure3_Nova_closure stabilitytest_Nova_PolymorphismStability_Nova_closure, void* stabilitytest_Nova_PolymorphismStability_ref_Nova_closure, void* closure_context);
void stabilitytest_Nova_PolymorphismStability_Nova_testProperty(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_PolymorphismStability_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_PolymorphismStability* stabilitytest_Nova_PolymorphismStability_Nova_construct(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_PolymorphismStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_PolymorphismStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_PolymorphismStability_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_PolymorphismStability_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_PolymorphismStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_PolymorphismStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_PolymorphismStability_Nova_destroy(stabilitytest_Nova_PolymorphismStability** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_PolymorphismStability_0_Nova_this(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_PolymorphismStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_PolymorphismStability_Nova_program);
}

void stabilitytest_Nova_PolymorphismStability_0_Nova_test(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Checking Polymorphism stability...")));
	stabilitytest_Nova_PolymorphismStability_Nova_testCalls(this, exceptionData);
	stabilitytest_Nova_PolymorphismStability_Nova_testClosure(this, exceptionData);
	stabilitytest_Nova_PolymorphismStability_Nova_testProperty(this, exceptionData);
}

void stabilitytest_Nova_PolymorphismStability_Nova_testCalls(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	stabilitytest_Nova_PolymorphicSuperClass* l1_Nova_obj1 = (stabilitytest_Nova_PolymorphicSuperClass*)nova_null;
	stabilitytest_Nova_PolymorphicSubClass* l1_Nova_obj2 = (stabilitytest_Nova_PolymorphicSubClass*)nova_null;
	
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Checking polymorphic method calls... ")));
	l1_Nova_obj1 = stabilitytest_Nova_PolymorphicSuperClass_Nova_construct(0, exceptionData);
	l1_Nova_obj2 = stabilitytest_Nova_PolymorphicSubClass_Nova_construct(0, exceptionData);
	if (!stabilitytest_Nova_PolymorphismStability_Nova_testSubCall(0, exceptionData, (stabilitytest_Nova_PolymorphicSuperClass*)(l1_Nova_obj2)))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Did not call sub class method")));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("OK")));
}

char stabilitytest_Nova_PolymorphismStability_Nova_testSubCall(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_PolymorphicSuperClass* stabilitytest_Nova_PolymorphismStability_Nova_obj)
{
	return nova_operators_Nova_Equals_virtual0_Nova_equals((nova_operators_Nova_Equals*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(stabilitytest_Nova_PolymorphismStability_Nova_obj), exceptionData)), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("sub class"))));
}

void stabilitytest_Nova_PolymorphismStability_Nova_testClosure(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	stabilitytest_Nova_PolymorphicSuperClass* l1_Nova_obj = (stabilitytest_Nova_PolymorphicSuperClass*)nova_null;
	nova_Nova_String* l1_Nova_child = (nova_Nova_String*)nova_null;
	
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Checking polymorphic closure method calls... ")));
	l1_Nova_obj = (stabilitytest_Nova_PolymorphicSuperClass*)(stabilitytest_Nova_PolymorphicSubClass_Nova_construct(0, exceptionData));
	if (!stabilitytest_Nova_PolymorphismStability_Nova_callPolymorphicClosure(0, exceptionData, (stabilitytest_Nova_PolymorphismStability_closure3_Nova_closure)l1_Nova_obj->vtable->nova_Nova_Object_virtual1_Nova_toString, l1_Nova_obj, nova_null))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Did not call sub class method as closure")));
	}
	stabilitytest_Nova_PolymorphicSuperClass_Nova_giveBirth(l1_Nova_obj, exceptionData);
	l1_Nova_child = (nova_Nova_String*)(nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(l1_Nova_obj->stabilitytest_Nova_PolymorphicSuperClass_Nova_child), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("!"))));
	if (!stabilitytest_Nova_PolymorphismStability_Nova_callPolymorphicClosure(0, exceptionData, (stabilitytest_Nova_PolymorphismStability_closure3_Nova_closure)l1_Nova_obj->stabilitytest_Nova_PolymorphicSuperClass_Nova_child->vtable->nova_Nova_Object_virtual1_Nova_toString, l1_Nova_obj->stabilitytest_Nova_PolymorphicSuperClass_Nova_child, nova_null))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Did not call sub class method as closure")));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("OK")));
}

char stabilitytest_Nova_PolymorphismStability_Nova_callPolymorphicClosure(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_PolymorphismStability_closure3_Nova_closure stabilitytest_Nova_PolymorphismStability_Nova_closure, void* stabilitytest_Nova_PolymorphismStability_ref_Nova_closure, void* closure_context)
{
	return nova_operators_Nova_Equals_virtual0_Nova_equals((nova_operators_Nova_Equals*)(stabilitytest_Nova_PolymorphismStability_Nova_closure(stabilitytest_Nova_PolymorphismStability_ref_Nova_closure, exceptionData, closure_context)), exceptionData, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("sub class"))));
}

void stabilitytest_Nova_PolymorphismStability_Nova_testProperty(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	stabilitytest_Nova_PolymorphicSuperClass* l1_Nova_obj1 = (stabilitytest_Nova_PolymorphicSuperClass*)nova_null;
	stabilitytest_Nova_PolymorphicSuperClass* l1_Nova_obj2 = (stabilitytest_Nova_PolymorphicSuperClass*)nova_null;
	
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Checking polymorphic properties... ")));
	l1_Nova_obj1 = stabilitytest_Nova_PolymorphicSuperClass_Nova_construct(0, exceptionData);
	l1_Nova_obj2 = (stabilitytest_Nova_PolymorphicSuperClass*)(stabilitytest_Nova_PolymorphicSubClass_Nova_construct(0, exceptionData));
	if (stabilitytest_Nova_PolymorphicSuperClass_virtual_Accessor_Nova_myProperty((stabilitytest_Nova_PolymorphicSuperClass*)(l1_Nova_obj1), exceptionData) != 1)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Did not call super polymorphic property accessor correctly")));
	}
	if (stabilitytest_Nova_PolymorphicSuperClass_virtual_Accessor_Nova_myProperty((stabilitytest_Nova_PolymorphicSuperClass*)(l1_Nova_obj2), exceptionData) != 2)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Did not call sub polymorphic property accessor correctly")));
	}
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("OK")));
}

void stabilitytest_Nova_PolymorphismStability_0_Nova_super(stabilitytest_Nova_PolymorphismStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

