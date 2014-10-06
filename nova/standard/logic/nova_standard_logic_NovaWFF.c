#include <precompiled.h>
#include <nova/standard/logic/nova_standard_logic_NovaWFF.h>


nova_VTable_nova_standard_logic_NovaWFF nova_VTable_nova_standard_logic_NovaWFF_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_logic_NovaStatementLetter** nova_standard_logic_NovaWFF_Novaletters;
	nova_standard_datastruct_NovaArrayList* nova_standard_logic_NovaWFF_Novahypotheses;
	nova_standard_logic_NovaConclusion* nova_standard_logic_NovaWFF_Novaconclusion;
	nova_standard_NovaString* nova_standard_logic_NovaWFF_Novawff;
	
)

void nova_standard_logic_NovaWFF_NovadecodeFormula(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_logic_NovaStatementGroup* nova_standard_logic_NovaWFF_NovasearchForConclusion(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff);
nova_standard_datastruct_NovaArrayList* nova_standard_logic_NovaWFF_NovadecodeHypotheses(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff);
nova_standard_logic_NovaStatementGroup* nova_standard_logic_NovaWFF_NovagenerateHypothesis(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff, int l0_Novastart, int l0_Novaend);
nova_standard_logic_NovaStatementGroup* nova_standard_logic_NovaWFF_NovasearchForStatement(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff);
int nova_standard_logic_NovaWFF_static_NovanextNonWhitespaceIndex(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff, int l0_Novaindex, int l0_Novadirection);
int nova_standard_logic_NovaWFF_static_Nova0_nextWhitespaceIndex(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff, int l0_Novaindex, int l0_Novadirection);
int nova_standard_logic_NovaWFF_static_Nova1_nextWhitespaceIndex(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff, int l0_Novaindex, int l0_Novadirection, char l0_Novaopposite);
char nova_standard_logic_NovaWFF_static_NovacontainsChar(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novaneedle, char* l0_NovaChars, int l0_Novalength);
int nova_standard_logic_NovaWFF_static_NovafindEndingMatch(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff, char l0_Novastart, char l0_Novaend, int l0_Novaindex, int l0_Novadirection);
char* nova_standard_logic_NovaWFF_static_Novawhitespace;
void nova_standard_logic_NovaWFFNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
		nova_standard_logic_NovaWFF_static_Novawhitespace = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_NovaChar[4]));
		nova_standard_logic_NovaWFF_static_Novawhitespace[0] = ' ';
		nova_standard_logic_NovaWFF_static_Novawhitespace[1] = '\t';
		nova_standard_logic_NovaWFF_static_Novawhitespace[2] = '\n';
		nova_standard_logic_NovaWFF_static_Novawhitespace[3] = '\r';
	}
}

nova_standard_logic_NovaWFF* nova_standard_logic_NovaWFF_Novaconstruct(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_NovawellFormedFormula, nova_standard_logic_NovaStatementLetter** l0_Novaletters)
{
	CCLASS_NEW(nova_standard_logic_NovaWFF, this);
	this->vtable = &nova_VTable_nova_standard_logic_NovaWFF_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_logic_NovaWFF_Novasuper(this, 0);
	
	{
		nova_standard_logic_NovaWFF_Novathis(this, exceptionData, l0_NovawellFormedFormula, l0_Novaletters);
	}
	
	return this;
}

void nova_del_WFF(nova_standard_logic_NovaWFF** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	NOVA_FREE((*this)->prv->nova_standard_logic_NovaWFF_Novaletters);
	nova_del_ArrayList(&(*this)->prv->nova_standard_logic_NovaWFF_Novahypotheses, exceptionData);
	nova_del_Conclusion(&(*this)->prv->nova_standard_logic_NovaWFF_Novaconclusion, exceptionData);
	nova_del_String(&(*this)->prv->nova_standard_logic_NovaWFF_Novawff, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_logic_NovaWFF_Novathis(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_NovawellFormedFormula, nova_standard_logic_NovaStatementLetter** l0_Novaletters)
{
	this->prv->nova_standard_logic_NovaWFF_Novawff = nova_standard_NovaString_Novatrim(l0_NovawellFormedFormula, exceptionData);
	this->prv->nova_standard_logic_NovaWFF_Novaletters = l0_Novaletters;
	this->prv->nova_standard_logic_NovaWFF_Novahypotheses = nova_standard_datastruct_NovaArrayList_Nova0_construct(0, exceptionData);
	nova_standard_logic_NovaWFF_NovadecodeFormula(this, exceptionData);
}

void nova_standard_logic_NovaWFF_NovadecodeFormula(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_logic_NovaStatementGroup* l1_NovaconclusionGroup;
	nova_standard_logic_NovaConclusion* l1_Novaconclusion;
	nova_standard_datastruct_NovaArrayList* l1_Novastatements;
	int l4_Novai;
	int l5_Novan;
	
	l1_NovaconclusionGroup = nova_standard_logic_NovaWFF_NovasearchForConclusion(this, exceptionData, this->prv->nova_standard_logic_NovaWFF_Novawff);
	if (l1_NovaconclusionGroup == (nova_standard_logic_NovaStatementGroup*)nova_null)
	{
		THROW(2, nova_standard_logic_NovaInvalidFormulaException_Novaconstruct(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "No valid conclusion found")));
	}
	l1_Novaconclusion = (nova_standard_logic_NovaConclusion*)(l1_NovaconclusionGroup->nova_standard_logic_NovaStatementGroup_Novastatement);
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Conclusion: "), exceptionData, l1_Novaconclusion->vtable->nova_standard_logic_NovaStatement_Novavirtual0_toString((nova_standard_logic_NovaStatement*)(l1_Novaconclusion), exceptionData)));
	this->prv->nova_standard_logic_NovaWFF_Novawff = nova_standard_datastruct_NovaBounds_NovaextractPreString(l1_NovaconclusionGroup->nova_standard_logic_NovaStatementGroup_Novabounds, exceptionData, this->prv->nova_standard_logic_NovaWFF_Novawff);
	l1_Novastatements = nova_standard_logic_NovaWFF_NovadecodeHypotheses(this, exceptionData, this->prv->nova_standard_logic_NovaWFF_Novawff);
	l4_Novai = 0;
	for (; l4_Novai < l1_Novastatements->nova_standard_datastruct_NovaArrayList_Novasize; l4_Novai++)
	{
		nova_standard_logic_NovaStatementGroup* l4_Novagroup;
		
		l4_Novagroup = (nova_standard_logic_NovaStatementGroup*)(((nova_standard_logic_NovaStatementGroup*)nova_standard_datastruct_NovaArrayList_Novaget(l1_Novastatements, exceptionData, l4_Novai)));
		nova_standard_datastruct_NovaArrayList_Novaadd(this->prv->nova_standard_logic_NovaWFF_Novahypotheses, exceptionData, l4_Novagroup->nova_standard_logic_NovaStatementGroup_Novastatement);
	}
	l5_Novan = 0;
	for (; l5_Novan < this->prv->nova_standard_logic_NovaWFF_Novahypotheses->nova_standard_datastruct_NovaArrayList_Novasize; l5_Novan++)
	{
		nova_standard_io_NovaConsole_static_Nova1_writeLine(0, exceptionData, (nova_standard_NovaObject*)(((nova_standard_logic_NovaHypothesis*)nova_standard_datastruct_NovaArrayList_Novaget(this->prv->nova_standard_logic_NovaWFF_Novahypotheses, exceptionData, l5_Novan))));
	}
}

nova_standard_logic_NovaStatementGroup* nova_standard_logic_NovaWFF_NovasearchForConclusion(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff)
{
	int l1_Novaindex;
	int l1_NovaconclusionStart;
	nova_standard_datastruct_NovaBounds* l1_Novabounds;
	nova_standard_logic_NovaConclusion* l1_Novaconclusion;
	nova_standard_logic_NovaStatementGroup* l1_Novagroup;
	
	l1_Novaindex = nova_standard_NovaString_NovalastIndexOf(l0_Novawff, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "->"));
	if (l1_Novaindex < 0)
	{
		return (nova_standard_logic_NovaStatementGroup*)nova_null;
	}
	if (nova_standard_NovaString_NovalastChar(l0_Novawff, exceptionData) == ')')
{
	int l3_Novafirst;
	
	l3_Novafirst = nova_standard_logic_NovaWFF_static_NovafindEndingMatch(this, exceptionData, l0_Novawff, '(', ')', l0_Novawff->nova_standard_NovaString_Novalength - 1, -1);
}
l1_NovaconclusionStart = nova_standard_logic_NovaWFF_static_NovanextNonWhitespaceIndex(this, exceptionData, l0_Novawff, l1_Novaindex + 2, 1);
l1_Novabounds = nova_standard_datastruct_NovaBounds_Novaconstruct(0, exceptionData, l1_NovaconclusionStart, l0_Novawff->nova_standard_NovaString_Novalength);
l1_Novaconclusion = nova_standard_logic_NovaConclusion_Novaconstruct(0, exceptionData, nova_standard_datastruct_NovaBounds_NovaextractString(l1_Novabounds, exceptionData, l0_Novawff));
l1_Novabounds->nova_standard_datastruct_NovaBounds_Novastart = nova_standard_logic_NovaWFF_static_NovanextNonWhitespaceIndex(this, exceptionData, l0_Novawff, l1_Novaindex - 1, -1) + 1;
l1_Novagroup = nova_standard_logic_NovaStatementGroup_Novaconstruct(0, exceptionData, (nova_standard_NovaObject*)(l1_Novaconclusion), l1_Novabounds);
return l1_Novagroup;}

nova_standard_datastruct_NovaArrayList* nova_standard_logic_NovaWFF_NovadecodeHypotheses(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff)
{
	nova_standard_datastruct_NovaArrayList* l1_Novalist;
	int l1_Novanext;
	int l1_Novaprev;
	int l2_Novaindex;
	
	l1_Novalist = nova_standard_datastruct_NovaArrayList_Nova0_construct(0, exceptionData);
	l1_Novanext = 0;
	l1_Novaprev = 0;
	l2_Novaindex = nova_standard_NovaString_Nova1_indexOf(l0_Novawff, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "&"));
	for (; l2_Novaindex >= 0; l2_Novaindex = nova_standard_NovaString_Nova2_indexOf(l0_Novawff, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "&"), l1_Novaprev + 1))
	{
		l1_Novanext = nova_standard_logic_NovaWFF_static_NovanextNonWhitespaceIndex(this, exceptionData, l0_Novawff, l2_Novaindex - 1, -1);
		nova_standard_datastruct_NovaArrayList_Novaadd(l1_Novalist, exceptionData, (nova_standard_NovaObject*)(nova_standard_logic_NovaWFF_NovagenerateHypothesis(this, exceptionData, l0_Novawff, l1_Novaprev, l1_Novanext + 1)));
		l1_Novaprev = nova_standard_logic_NovaWFF_static_NovanextNonWhitespaceIndex(this, exceptionData, l0_Novawff, l2_Novaindex + 1, 1);
	}
	nova_standard_datastruct_NovaArrayList_Novaadd(l1_Novalist, exceptionData, (nova_standard_NovaObject*)(nova_standard_logic_NovaWFF_NovagenerateHypothesis(this, exceptionData, l0_Novawff, l1_Novaprev, l0_Novawff->nova_standard_NovaString_Novalength)));
	return l1_Novalist;
}

nova_standard_logic_NovaStatementGroup* nova_standard_logic_NovaWFF_NovagenerateHypothesis(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff, int l0_Novastart, int l0_Novaend)
{
	nova_standard_datastruct_NovaBounds* l1_Novabounds;
	nova_standard_logic_NovaHypothesis* l1_Novah;
	nova_standard_logic_NovaStatementGroup* l1_Novagroup;
	
	l1_Novabounds = nova_standard_datastruct_NovaBounds_Novaconstruct(0, exceptionData, l0_Novastart, l0_Novaend);
	l1_Novah = nova_standard_logic_NovaHypothesis_Nova0_construct(0, exceptionData, nova_standard_datastruct_NovaBounds_NovaextractString(l1_Novabounds, exceptionData, l0_Novawff));
	l1_Novagroup = nova_standard_logic_NovaStatementGroup_Novaconstruct(0, exceptionData, (nova_standard_NovaObject*)(l1_Novah), l1_Novabounds);
	return l1_Novagroup;
}

nova_standard_logic_NovaStatementGroup* nova_standard_logic_NovaWFF_NovasearchForStatement(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff)
{
	return (nova_standard_logic_NovaStatementGroup*)nova_null;
}

int nova_standard_logic_NovaWFF_static_NovanextNonWhitespaceIndex(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff, int l0_Novaindex, int l0_Novadirection)
{
	return nova_standard_logic_NovaWFF_static_Nova1_nextWhitespaceIndex((nova_standard_logic_NovaWFF*)nova_null, exceptionData, l0_Novawff, l0_Novaindex, l0_Novadirection, 1);
}

int nova_standard_logic_NovaWFF_static_Nova0_nextWhitespaceIndex(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff, int l0_Novaindex, int l0_Novadirection)
{
	return nova_standard_logic_NovaWFF_static_Nova1_nextWhitespaceIndex((nova_standard_logic_NovaWFF*)nova_null, exceptionData, l0_Novawff, l0_Novaindex, l0_Novadirection, 0);
}

int nova_standard_logic_NovaWFF_static_Nova1_nextWhitespaceIndex(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff, int l0_Novaindex, int l0_Novadirection, char l0_Novaopposite)
{
	int l2_Novai;
	
	l2_Novai = l0_Novaindex;
	for (; l2_Novai < l0_Novawff->nova_standard_NovaString_Novalength && l2_Novai >= 0; l2_Novai = l2_Novai + l0_Novadirection)
	{
		char l2_Novac;
		
		l2_Novac = nova_standard_NovaString_NovaCharAt(l0_Novawff, exceptionData, l2_Novai);
		if (nova_standard_logic_NovaWFF_static_NovacontainsChar((nova_standard_logic_NovaWFF*)nova_null, exceptionData, l2_Novac, nova_standard_logic_NovaWFF_static_Novawhitespace, 4) != l0_Novaopposite)
		{
			return l2_Novai;
		}
	}
	return -1;
}

char nova_standard_logic_NovaWFF_static_NovacontainsChar(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novaneedle, char* l0_NovaChars, int l0_Novalength)
{
	int l2_Novai;
	
	l2_Novai = 0;
	for (; l2_Novai < l0_Novalength; l2_Novai++)
	{
		if (l0_Novaneedle == l0_NovaChars[l2_Novai])
		{
			return 1;
		}
	}
	return 0;
}

int nova_standard_logic_NovaWFF_static_NovafindEndingMatch(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novawff, char l0_Novastart, char l0_Novaend, int l0_Novaindex, int l0_Novadirection)
{
	int l1_Novascope;
	int l3_Novai;
	
	if (l0_Novadirection < 0)
	{
		char l2_Novatemp;
		
		l2_Novatemp = l0_Novastart;
		l0_Novastart = l0_Novaend;
		l0_Novaend = l2_Novatemp;
	}
	l1_Novascope = 0;
	l3_Novai = l0_Novaindex;
	for (; l3_Novai < l0_Novawff->nova_standard_NovaString_Novalength && l3_Novai >= 0; l3_Novai = l3_Novai + l0_Novadirection)
	{
		char l3_Novac;
		
		l3_Novac = nova_standard_NovaString_NovaCharAt(l0_Novawff, exceptionData, l3_Novai);
		if (l3_Novac == l0_Novastart)
		{
			l1_Novascope++;
		}
		else if (l3_Novac == l0_Novaend)
		{
			l1_Novascope--;
		}
		if (l1_Novascope == 0)
		{
			return l3_Novai;
		}
	}
	return -1;
}

void nova_standard_logic_NovaWFF_Novasuper(nova_standard_logic_NovaWFF* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_logic_NovaWFF_Novaletters = (nova_standard_logic_NovaStatementLetter**)nova_null;
	this->prv->nova_standard_logic_NovaWFF_Novahypotheses = (nova_standard_datastruct_NovaArrayList*)nova_null;
	this->prv->nova_standard_logic_NovaWFF_Novaconclusion = (nova_standard_logic_NovaConclusion*)nova_null;
	this->prv->nova_standard_logic_NovaWFF_Novawff = (nova_standard_NovaString*)nova_null;
}
