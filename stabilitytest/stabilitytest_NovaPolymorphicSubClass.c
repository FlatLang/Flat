#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaPolymorphicSubClass.h>


nova_VTable_stabilitytest_NovaPolymorphicSubClass nova_VTable_stabilitytest_NovaPolymorphicSubClass_val =
{
	stabilitytest_NovaPolymorphicSubClass_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void stabilitytest_NovaPolymorphicSubClassNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaPolymorphicSubClass* stabilitytest_NovaPolymorphicSubClass_Nova0_construct(stabilitytest_NovaPolymorphicSubClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaPolymorphicSubClass, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaPolymorphicSubClass_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	stabilitytest_NovaPolymorphicSuperClass_Novasuper((stabilitytest_NovaPolymorphicSuperClass*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaPolymorphicSuperClass_Novathis((stabilitytest_NovaPolymorphicSuperClass*)(this), exceptionData);
	stabilitytest_NovaPolymorphicSubClass_Novasuper(this, 0);
	
	{
		stabilitytest_NovaPolymorphicSubClass_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_PolymorphicSubClass(stabilitytest_NovaPolymorphicSubClass** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

nova_standard_NovaString* stabilitytest_NovaPolymorphicSubClass_Nova0_toString(stabilitytest_NovaPolymorphicSubClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_Nova1_construct(0, exceptionData, "sub class");
}

void stabilitytest_NovaPolymorphicSubClass_Novathis(stabilitytest_NovaPolymorphicSubClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaPolymorphicSubClass_Novasuper(stabilitytest_NovaPolymorphicSubClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
