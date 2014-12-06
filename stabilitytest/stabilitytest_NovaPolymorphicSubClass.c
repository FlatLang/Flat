#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaPolymorphicSubClass.h>


nova_VTable_stabilitytest_NovaPolymorphicSubClass nova_VTable_stabilitytest_NovaPolymorphicSubClass_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	stabilitytest_NovaPolymorphicSubClass_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
};
void stabilitytest_NovaPolymorphicSubClassNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaPolymorphicSubClass* stabilitytest_NovaPolymorphicSubClass_0_Novaconstruct(stabilitytest_NovaPolymorphicSubClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaPolymorphicSubClass, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaPolymorphicSubClass_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	stabilitytest_NovaPolymorphicSuperClass_Novasuper((stabilitytest_NovaPolymorphicSuperClass*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaPolymorphicSuperClass_Novathis((stabilitytest_NovaPolymorphicSuperClass*)(this), exceptionData);
	stabilitytest_NovaPolymorphicSubClass_Novasuper(this, exceptionData);
	
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

nova_standard_NovaString* stabilitytest_NovaPolymorphicSubClass_0_NovatoString(stabilitytest_NovaPolymorphicSubClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "sub class");
}

void stabilitytest_NovaPolymorphicSubClass_Novathis(stabilitytest_NovaPolymorphicSubClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaPolymorphicSubClass_Novasuper(stabilitytest_NovaPolymorphicSubClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
