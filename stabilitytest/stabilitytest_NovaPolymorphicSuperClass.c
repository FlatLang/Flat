#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaPolymorphicSuperClass.h>


nova_VTable_stabilitytest_NovaPolymorphicSuperClass nova_VTable_stabilitytest_NovaPolymorphicSuperClass_val =
{
	stabilitytest_NovaPolymorphicSuperClass_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void stabilitytest_NovaPolymorphicSuperClassNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaPolymorphicSuperClass* stabilitytest_NovaPolymorphicSuperClass_Nova0_construct(stabilitytest_NovaPolymorphicSuperClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaPolymorphicSuperClass, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaPolymorphicSuperClass_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaPolymorphicSuperClass_Novasuper(this, 0);
	
	{
		stabilitytest_NovaPolymorphicSuperClass_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_PolymorphicSuperClass(stabilitytest_NovaPolymorphicSuperClass** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_PolymorphicSubClass(&(*this)->stabilitytest_NovaPolymorphicSuperClass_Novachild, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaPolymorphicSuperClass_NovagiveBirth(stabilitytest_NovaPolymorphicSuperClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->stabilitytest_NovaPolymorphicSuperClass_Novachild = stabilitytest_NovaPolymorphicSubClass_Nova0_construct(0, exceptionData);
}

nova_standard_NovaString* stabilitytest_NovaPolymorphicSuperClass_Nova0_toString(stabilitytest_NovaPolymorphicSuperClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_Nova1_construct(0, exceptionData, "super class");
}

void stabilitytest_NovaPolymorphicSuperClass_Novathis(stabilitytest_NovaPolymorphicSuperClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaPolymorphicSuperClass_Novasuper(stabilitytest_NovaPolymorphicSuperClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->stabilitytest_NovaPolymorphicSuperClass_Novachild = (stabilitytest_NovaPolymorphicSubClass*)nova_null;
}
