#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaPolymorphismStability.h>

typedef nova_standard_NovaString* (*l0_Nova1_closure)(void*, nova_standard_exception_NovaExceptionData*);
typedef nova_standard_NovaString* (*l0_Nova2_closure)(void*, nova_standard_exception_NovaExceptionData*);

nova_VTable_stabilitytest_NovaPolymorphismStability nova_VTable_stabilitytest_NovaPolymorphismStability_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};

void stabilitytest_NovaPolymorphismStability_static_NovatestCalls(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
char stabilitytest_NovaPolymorphismStability_static_NovatestSubCall(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaPolymorphicSuperClass* l0_Novaobj);
void stabilitytest_NovaPolymorphismStability_static_NovatestClosure(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
char stabilitytest_NovaPolymorphismStability_static_NovacallPolymorphicClosure(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova2_closure l0_Novaclosure, void* l0_Novaref_closure);
void stabilitytest_NovaPolymorphismStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaPolymorphismStability* stabilitytest_NovaPolymorphismStability_Nova0_construct(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaPolymorphismStability, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaPolymorphismStability_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaPolymorphismStability_Novasuper(this, 0);
	
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
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Checking Polymorphism stability..."));
	stabilitytest_NovaPolymorphismStability_static_NovatestCalls((stabilitytest_NovaPolymorphismStability*)nova_null, exceptionData, l0_Novaprogram);
	stabilitytest_NovaPolymorphismStability_static_NovatestClosure((stabilitytest_NovaPolymorphismStability*)nova_null, exceptionData, l0_Novaprogram);
}

void stabilitytest_NovaPolymorphismStability_static_NovatestCalls(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	stabilitytest_NovaPolymorphicSuperClass* l1_Novaobj1;
	stabilitytest_NovaPolymorphicSubClass* l1_Novaobj2;
	
	nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Checking polymorphic method calls... "));
	l1_Novaobj1 = stabilitytest_NovaPolymorphicSuperClass_Nova0_construct(0, exceptionData);
	l1_Novaobj2 = stabilitytest_NovaPolymorphicSubClass_Nova0_construct(0, exceptionData);
	if (!stabilitytest_NovaPolymorphismStability_static_NovatestSubCall((stabilitytest_NovaPolymorphismStability*)nova_null, exceptionData, (stabilitytest_NovaPolymorphicSuperClass*)(l1_Novaobj2)))
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Did not call sub class method"));
	}
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "OK"));
}

char stabilitytest_NovaPolymorphismStability_static_NovatestSubCall(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaPolymorphicSuperClass* l0_Novaobj)
{
	nova_standard_NovaString* nova_local_0;
	
	nova_local_0 = l0_Novaobj->vtable->stabilitytest_NovaPolymorphicSuperClass_Novavirtual0_toString(l0_Novaobj, exceptionData);
	return nova_local_0->vtable->nova_standard_NovaString_Novavirtual_equals(nova_local_0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "sub class"));
}

void stabilitytest_NovaPolymorphismStability_static_NovatestClosure(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	stabilitytest_NovaPolymorphicSuperClass* l1_Novaobj;
	nova_standard_NovaString* l1_Novachild;
	nova_standard_NovaString* nova_local_0;
	
	nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Checking polymorphic closure method calls... "));
	l1_Novaobj = (stabilitytest_NovaPolymorphicSuperClass*)(stabilitytest_NovaPolymorphicSubClass_Nova0_construct(0, exceptionData));
	if (!stabilitytest_NovaPolymorphismStability_static_NovacallPolymorphicClosure((stabilitytest_NovaPolymorphismStability*)nova_null, exceptionData, (l0_Nova2_closure)l1_Novaobj->vtable->stabilitytest_NovaPolymorphicSuperClass_Novavirtual0_toString, l1_Novaobj))
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Did not call sub class method as closure"));
	}
	stabilitytest_NovaPolymorphicSuperClass_NovagiveBirth(l1_Novaobj, exceptionData);
	nova_local_0 = l1_Novaobj->stabilitytest_NovaPolymorphicSuperClass_Novachild->vtable->stabilitytest_NovaPolymorphicSubClass_Novavirtual0_toString(l1_Novaobj->stabilitytest_NovaPolymorphicSuperClass_Novachild, exceptionData);
	l1_Novachild = nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "!"));
	if (!stabilitytest_NovaPolymorphismStability_static_NovacallPolymorphicClosure((stabilitytest_NovaPolymorphismStability*)nova_null, exceptionData, (l0_Nova2_closure)l1_Novaobj->stabilitytest_NovaPolymorphicSuperClass_Novachild->vtable->stabilitytest_NovaPolymorphicSubClass_Novavirtual0_toString, l1_Novaobj->stabilitytest_NovaPolymorphicSuperClass_Novachild))
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Did not call sub class method as closure"));
	}
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "OK"));
}

char stabilitytest_NovaPolymorphismStability_static_NovacallPolymorphicClosure(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova2_closure l0_Novaclosure, void* l0_Novaref_closure)
{
	nova_standard_NovaString* nova_local_0;
	
	nova_local_0 = l0_Novaclosure(l0_Novaref_closure, exceptionData);
	return nova_local_0->vtable->nova_standard_NovaString_Novavirtual_equals(nova_local_0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "sub class"));
}

void stabilitytest_NovaPolymorphismStability_Novathis(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaPolymorphismStability_Novasuper(stabilitytest_NovaPolymorphismStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
