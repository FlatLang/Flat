#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaClosureStability.h>

typedef int (*l0_Nova1_closure)(void*, nova_standard_exception_NovaExceptionData*, int, int);
typedef int (*l0_Nova2_closure)(void*, nova_standard_exception_NovaExceptionData*, int, int);
typedef double (*l0_Nova3_closure)(void*, nova_standard_exception_NovaExceptionData*, double);
typedef double (*l0_Nova4_closure)(void*, nova_standard_exception_NovaExceptionData*, double);
typedef void (*l0_Nova5_closure)(void*, nova_standard_exception_NovaExceptionData*);
typedef void (*l0_Nova6_closure)(void*, nova_standard_exception_NovaExceptionData*);

nova_VTable_stabilitytest_NovaClosureStability nova_VTable_stabilitytest_NovaClosureStability_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	int stabilitytest_NovaClosureStability_Novanumber;
	
)

void stabilitytest_NovaClosureStability_NovaincrementNumber(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData);
void stabilitytest_NovaClosureStability_static_NovatestClosures(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaClosureStability_static_NovatestMathClosures(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaClosureStability_static_NovatestInstanceClosure(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
int stabilitytest_NovaClosureStability_static_NovacallClosure(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova2_closure l0_Novaclosure, void* l0_Novaref_closure, int l0_Novaa, int l0_Novab);
double stabilitytest_NovaClosureStability_static_NovamathClosure(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova4_closure l0_Novaclosure, void* l0_Novaref_closure, double l0_Novavalue);
void stabilitytest_NovaClosureStability_static_NovainstanceClosure(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova6_closure l0_Novaclosure, void* l0_Novaref_closure);
int stabilitytest_NovaClosureStability_static_Novamultiply(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novavalue1, int l0_Novavalue2);
int stabilitytest_NovaClosureStability_static_Novapow(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novabase, int l0_Novapow);
double stabilitytest_NovaClosureStability_static_NovaTOLERANCE;
void stabilitytest_NovaClosureStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaClosureStability* stabilitytest_NovaClosureStability_Nova0_construct(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaClosureStability, this);
	this->vtable = &nova_VTable_stabilitytest_NovaClosureStability_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaClosureStability_Novasuper(this, 0);
	
	{
		stabilitytest_NovaClosureStability_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_ClosureStability(stabilitytest_NovaClosureStability** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaClosureStability_NovaincrementNumber(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->stabilitytest_NovaClosureStability_Novanumber++;
}

void stabilitytest_NovaClosureStability_static_Novatest(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	stabilitytest_NovaClosureStability_static_NovaTOLERANCE = 0.0000000001;
	stabilitytest_NovaClosureStability_static_NovatestClosures((stabilitytest_NovaClosureStability*)nova_null, exceptionData, l0_Novaprogram);
}

void stabilitytest_NovaClosureStability_static_NovatestClosures(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Testing closures..."));
	stabilitytest_NovaClosureStability_static_NovatestMathClosures((stabilitytest_NovaClosureStability*)nova_null, exceptionData, l0_Novaprogram);
	stabilitytest_NovaClosureStability_static_NovatestInstanceClosure((stabilitytest_NovaClosureStability*)nova_null, exceptionData, l0_Novaprogram);
}

void stabilitytest_NovaClosureStability_static_NovatestMathClosures(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	int l1_Novaa;
	int l1_Novab;
	double l1_Novavalue;
	
	nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Testing static math closures... "));
	l1_Novaa = 5;
	l1_Novab = 3;
	l1_Novavalue = 0.5;
	if (stabilitytest_NovaClosureStability_static_NovacallClosure((stabilitytest_NovaClosureStability*)nova_null, exceptionData, (l0_Nova2_closure)&stabilitytest_NovaClosureStability_static_Novamultiply, (stabilitytest_NovaClosureStability*)nova_null, l1_Novaa, l1_Novab) != stabilitytest_NovaClosureStability_static_Novamultiply((stabilitytest_NovaClosureStability*)nova_null, exceptionData, l1_Novaa, l1_Novab))
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to call multiply(Int, Int) closure"));
	}
	if (stabilitytest_NovaClosureStability_static_NovacallClosure((stabilitytest_NovaClosureStability*)nova_null, exceptionData, (l0_Nova2_closure)&stabilitytest_NovaClosureStability_static_Novapow, (stabilitytest_NovaClosureStability*)nova_null, l1_Novaa, l1_Novab) != stabilitytest_NovaClosureStability_static_Novapow((stabilitytest_NovaClosureStability*)nova_null, exceptionData, l1_Novaa, l1_Novab))
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to call pow(Int, Int) closure"));
	}
	if (stabilitytest_NovaClosureStability_static_NovamathClosure((stabilitytest_NovaClosureStability*)nova_null, exceptionData, (l0_Nova4_closure)&nova_standard_NovaMath_static_Novasin, 0, l1_Novavalue) - nova_standard_NovaMath_static_Novasin(0, exceptionData, l1_Novavalue) >= stabilitytest_NovaClosureStability_static_NovaTOLERANCE)
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to call Math.sin(Double) closure"));
	}
	if (stabilitytest_NovaClosureStability_static_NovamathClosure((stabilitytest_NovaClosureStability*)nova_null, exceptionData, (l0_Nova4_closure)&nova_standard_NovaMath_static_Novatan, 0, l1_Novavalue) - nova_standard_NovaMath_static_Novatan(0, exceptionData, l1_Novavalue) >= stabilitytest_NovaClosureStability_static_NovaTOLERANCE)
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to call Math.tan(Double) closure"));
	}
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "OK"));
}

void stabilitytest_NovaClosureStability_static_NovatestInstanceClosure(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	stabilitytest_NovaClosureStability* l1_Novac;
	
	nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Testing instance closures... "));
	l1_Novac = stabilitytest_NovaClosureStability_Nova0_construct(0, exceptionData);
	stabilitytest_NovaClosureStability_static_NovainstanceClosure((stabilitytest_NovaClosureStability*)nova_null, exceptionData, (l0_Nova6_closure)&stabilitytest_NovaClosureStability_NovaincrementNumber, l1_Novac);
	if (l1_Novac->prv->stabilitytest_NovaClosureStability_Novanumber == 0)
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to call incrementNumber() instance closure"));
	}
	stabilitytest_NovaClosureStability_static_NovainstanceClosure((stabilitytest_NovaClosureStability*)nova_null, exceptionData, (l0_Nova6_closure)&stabilitytest_NovaClosureStability_NovaincrementNumber, stabilitytest_NovaClosureStability_Nova0_construct(0, exceptionData));
	stabilitytest_NovaClosureStability_static_NovainstanceClosure((stabilitytest_NovaClosureStability*)nova_null, exceptionData, (l0_Nova6_closure)&stabilitytest_NovaClosureStability_NovaincrementNumber, l1_Novac);
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "OK"));
}

int stabilitytest_NovaClosureStability_static_NovacallClosure(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova2_closure l0_Novaclosure, void* l0_Novaref_closure, int l0_Novaa, int l0_Novab)
{
	return l0_Novaclosure(l0_Novaref_closure, exceptionData, l0_Novaa, l0_Novab);
}

double stabilitytest_NovaClosureStability_static_NovamathClosure(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova4_closure l0_Novaclosure, void* l0_Novaref_closure, double l0_Novavalue)
{
	return l0_Novaclosure(l0_Novaref_closure, exceptionData, l0_Novavalue);
}

void stabilitytest_NovaClosureStability_static_NovainstanceClosure(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova6_closure l0_Novaclosure, void* l0_Novaref_closure)
{
	l0_Novaclosure(l0_Novaref_closure, exceptionData);
}

int stabilitytest_NovaClosureStability_static_Novamultiply(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novavalue1, int l0_Novavalue2)
{
	return l0_Novavalue1 * l0_Novavalue2;
}

int stabilitytest_NovaClosureStability_static_Novapow(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novabase, int l0_Novapow)
{
	int l1_Novavalue;
	int l2_Novai;
	
	l1_Novavalue = l0_Novabase;
	l2_Novai = 0;
	for (; l2_Novai < l0_Novapow - 1; l2_Novai++)
	{
		l1_Novavalue = l1_Novavalue * l0_Novabase;
	}
	return l1_Novavalue;
}

void stabilitytest_NovaClosureStability_Novathis(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaClosureStability_Novasuper(stabilitytest_NovaClosureStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->stabilitytest_NovaClosureStability_Novanumber = 0;
}
