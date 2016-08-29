#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_PolymorphicSuperClass.h>



stabilitytest_Extension_VTable_PolymorphicSuperClass stabilitytest_Extension_VTable_PolymorphicSuperClass_val =
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
	stabilitytest_Nova_PolymorphicSuperClass_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	stabilitytest_Nova_PolymorphicSuperClass_Accessor_Nova_myProperty,
};




void stabilitytest_Nova_PolymorphicSuperClass_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_PolymorphicSuperClass* stabilitytest_Nova_PolymorphicSuperClass_Nova_construct(stabilitytest_Nova_PolymorphicSuperClass* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_Nova_PolymorphicSuperClass, this,);
	this->vtable = &stabilitytest_Extension_VTable_PolymorphicSuperClass_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_PolymorphicSuperClass_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_PolymorphicSuperClass_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void stabilitytest_Nova_PolymorphicSuperClass_Nova_destroy(stabilitytest_Nova_PolymorphicSuperClass** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	stabilitytest_Nova_PolymorphicSubClass_Nova_destroy(&(*this)->stabilitytest_Nova_PolymorphicSuperClass_Nova_child, exceptionData);
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_PolymorphicSuperClass_Nova_giveBirth(stabilitytest_Nova_PolymorphicSuperClass* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->stabilitytest_Nova_PolymorphicSuperClass_Nova_child = stabilitytest_Nova_PolymorphicSubClass_Nova_construct(0, exceptionData);
}

nova_Nova_String* stabilitytest_Nova_PolymorphicSuperClass_0_Nova_toString(stabilitytest_Nova_PolymorphicSuperClass* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("super class"));
}

void stabilitytest_Nova_PolymorphicSuperClass_0_Nova_this(stabilitytest_Nova_PolymorphicSuperClass* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

int stabilitytest_Nova_PolymorphicSuperClass_Accessor_Nova_myProperty(stabilitytest_Nova_PolymorphicSuperClass* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (int)1;
}


void stabilitytest_Nova_PolymorphicSuperClass_Nova_super(stabilitytest_Nova_PolymorphicSuperClass* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->stabilitytest_Nova_PolymorphicSuperClass_Nova_child = (stabilitytest_Nova_PolymorphicSubClass*)nova_null;
}

int stabilitytest_Nova_PolymorphicSuperClass_virtual_Accessor_Nova_myProperty(stabilitytest_Nova_PolymorphicSuperClass* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->vtable->stabilitytest_Nova_PolymorphicSuperClass_virtual_Accessor_Nova_myProperty((stabilitytest_Nova_PolymorphicSuperClass*)(this), exceptionData);
}

