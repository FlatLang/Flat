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
	},
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	stabilitytest_Nova_PolymorphicSuperClass_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void stabilitytest_Nova_PolymorphicSuperClassNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_PolymorphicSuperClass* stabilitytest_Nova_PolymorphicSuperClass_2_Nova_construct(stabilitytest_Nova_PolymorphicSuperClass* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_Nova_PolymorphicSuperClass, this,);
	this->vtable = &stabilitytest_Extension_VTable_PolymorphicSuperClass_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	stabilitytest_Nova_PolymorphicSuperClass_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_PolymorphicSuperClass_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void stabilitytest_Nova_PolymorphicSuperClass_Nova_destroy(stabilitytest_Nova_PolymorphicSuperClass** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	stabilitytest_Nova_PolymorphicSubClass_Nova_destroy(&(*this)->stabilitytest_Nova_PolymorphicSuperClass_Nova_child, exceptionData);
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_PolymorphicSuperClass_Nova_giveBirth(stabilitytest_Nova_PolymorphicSuperClass* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->stabilitytest_Nova_PolymorphicSuperClass_Nova_child = stabilitytest_Nova_PolymorphicSubClass_2_Nova_construct(0, exceptionData);
}

nova_standard_Nova_String* stabilitytest_Nova_PolymorphicSuperClass_0_Nova_toString(stabilitytest_Nova_PolymorphicSuperClass* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "super class");
}

void stabilitytest_Nova_PolymorphicSuperClass_2_Nova_this(stabilitytest_Nova_PolymorphicSuperClass* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void stabilitytest_Nova_PolymorphicSuperClass_Nova_super(stabilitytest_Nova_PolymorphicSuperClass* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->stabilitytest_Nova_PolymorphicSuperClass_Nova_child = (stabilitytest_Nova_PolymorphicSubClass*)nova_null;
}

