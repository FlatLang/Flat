#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaPolymorphismStability.h>

typedef nova_standard_NovaString* (*l0_1_Novaclosure)(void*, nova_standard_exception_NovaExceptionData*);
typedef nova_standard_NovaString* (*l0_2_Novaclosure)(void*, nova_standard_exception_NovaExceptionData*);

nova_VTable_stabilitytest_NovaPolymorphismStability nova_VTable_stabilitytest_NovaPolymorphismStability_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
};

void stabilitytest_NovaPolymorphismStability_static_NovatestCalls(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
char stabilitytest_NovaPolymorphismStability_static_NovatestSubCall(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaPolymorphicSuperClass* l0_Novaobj);
void stabilitytest_NovaPolymorphismStability_static_NovatestClosure(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
char stabilitytest_NovaPolymorphismStability_static_NovacallPolymorphicClosure(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_2_Novaclosure l0_Novaclosure, void* l0_ref_Novaclosure);
void stabilitytest_NovaPolymorphismStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaPolymorphismStability* stabilitytest_NovaPolymorphismStability_0_Novaconstruct(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaPolymorphismStability, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaPolymorphismStability_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaPolymorphismStability_Novasuper(this, exceptionData);
	
	{
		stabilitytest_NovaPolymorphismStability_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_PolymorphismStability(stabilitytest_NovaPolymorphismStability** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaPolymorphismStability_static_Novatest(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking Polymorphism stability..."));
	stabilitytest_NovaPolymorphismStability_static_NovatestCalls((stabilitytest_NovaPolymorphismStability*)nova_null, exceptionData, l0_Novaprogram);
	stabilitytest_NovaPolymorphismStability_static_NovatestClosure((stabilitytest_NovaPolymorphismStability*)nova_null, exceptionData, l0_Novaprogram);
}

void stabilitytest_NovaPolymorphismStability_static_NovatestCalls(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	stabilitytest_NovaPolymorphicSuperClass* l1_Novaobj1;
	stabilitytest_NovaPolymorphicSubClass* l1_Novaobj2;
	
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking polymorphic method calls... "));
	l1_Novaobj1 = stabilitytest_NovaPolymorphicSuperClass_0_Novaconstruct(0, exceptionData);
	l1_Novaobj2 = stabilitytest_NovaPolymorphicSubClass_0_Novaconstruct(0, exceptionData);
	if (!stabilitytest_NovaPolymorphismStability_static_NovatestSubCall((stabilitytest_NovaPolymorphismStability*)nova_null, exceptionData, (stabilitytest_NovaPolymorphicSuperClass*)(l1_Novaobj2)))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Did not call sub class method"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
}

char stabilitytest_NovaPolymorphismStability_static_NovatestSubCall(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaPolymorphicSuperClass* l0_Novaobj)
{
	nova_standard_NovaString* nova_local_0;
	
	nova_local_0 = l0_Novaobj->vtable->stabilitytest_NovaPolymorphicSuperClass_virtual0_NovatoString(l0_Novaobj, exceptionData);
	return nova_local_0->vtable->nova_standard_NovaString_virtual_Novaequals(nova_local_0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "sub class"));
}

void stabilitytest_NovaPolymorphismStability_static_NovatestClosure(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	stabilitytest_NovaPolymorphicSuperClass* l1_Novaobj;
	nova_standard_NovaString* l1_Novachild;
	nova_standard_NovaString* nova_local_0;
	
	nova_standard_io_NovaConsole_static_0_Novawrite(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Checking polymorphic closure method calls... "));
	l1_Novaobj = (stabilitytest_NovaPolymorphicSuperClass*)(stabilitytest_NovaPolymorphicSubClass_0_Novaconstruct(0, exceptionData));
	if (!stabilitytest_NovaPolymorphismStability_static_NovacallPolymorphicClosure((stabilitytest_NovaPolymorphismStability*)nova_null, exceptionData, (l0_2_Novaclosure)l1_Novaobj->vtable->stabilitytest_NovaPolymorphicSuperClass_virtual0_NovatoString, l1_Novaobj))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Did not call sub class method as closure"));
	}
	stabilitytest_NovaPolymorphicSuperClass_NovagiveBirth(l1_Novaobj, exceptionData);
	nova_local_0 = l1_Novaobj->stabilitytest_NovaPolymorphicSuperClass_Novachild->vtable->stabilitytest_NovaPolymorphicSubClass_virtual0_NovatoString(l1_Novaobj->stabilitytest_NovaPolymorphicSuperClass_Novachild, exceptionData);
	l1_Novachild = nova_local_0->vtable->nova_standard_NovaString_virtual0_Novaconcat(nova_local_0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "!"));
	if (!stabilitytest_NovaPolymorphismStability_static_NovacallPolymorphicClosure((stabilitytest_NovaPolymorphismStability*)nova_null, exceptionData, (l0_2_Novaclosure)l1_Novaobj->stabilitytest_NovaPolymorphicSuperClass_Novachild->vtable->stabilitytest_NovaPolymorphicSubClass_virtual0_NovatoString, l1_Novaobj->stabilitytest_NovaPolymorphicSuperClass_Novachild))
	{
		stabilitytest_NovaStabilityTest_1_Novafail(l0_Novaprogram, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "Did not call sub class method as closure"));
	}
	nova_standard_io_NovaConsole_static_0_NovawriteLine(0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "OK"));
}

char stabilitytest_NovaPolymorphismStability_static_NovacallPolymorphicClosure(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_2_Novaclosure l0_Novaclosure, void* l0_ref_Novaclosure)
{
	nova_standard_NovaString* nova_local_0;
	
	nova_local_0 = l0_Novaclosure(l0_ref_Novaclosure, exceptionData);
	return nova_local_0->vtable->nova_standard_NovaString_virtual_Novaequals(nova_local_0, exceptionData, nova_standard_NovaString_1_Novaconstruct(0, exceptionData, "sub class"));
}

void stabilitytest_NovaPolymorphismStability_Novathis(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaPolymorphismStability_Novasuper(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
