#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaPolymorphicSuperClass.h>


nova_VTable_stabilitytest_NovaPolymorphicSuperClass nova_VTable_stabilitytest_NovaPolymorphicSuperClass_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	stabilitytest_NovaPolymorphicSuperClass_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
};
void stabilitytest_NovaPolymorphicSuperClassNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaPolymorphicSuperClass* stabilitytest_NovaPolymorphicSuperClass_0_Novaconstruct(stabilitytest_NovaPolymorphicSuperClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaPolymorphicSuperClass, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaPolymorphicSuperClass_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaPolymorphicSuperClass_Novasuper(this, exceptionData);
	
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
	this->stabilitytest_NovaPolymorphicSuperClass_Novachild = stabilitytest_NovaPolymorphicSubClass_0_Novaconstruct(0, exceptionData);
}

nova_standard_NovaString* stabilitytest_NovaPolymorphicSuperClass_0_NovatoString(stabilitytest_NovaPolymorphicSuperClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "super class");
}

void stabilitytest_NovaPolymorphicSuperClass_Novathis(stabilitytest_NovaPolymorphicSuperClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaPolymorphicSuperClass_Novasuper(stabilitytest_NovaPolymorphicSuperClass* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->stabilitytest_NovaPolymorphicSuperClass_Novachild = (stabilitytest_NovaPolymorphicSubClass*)nova_null;
}
