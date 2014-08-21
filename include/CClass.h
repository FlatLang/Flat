#ifndef CClass_CClass_h
#define CClass_CClass_h

#include <MacroLib.h>

#define CCLASS_NEW1(_CLASS_) (_CLASS_*)NOVA_MALLOC(sizeof(_CLASS_))
#define CCLASS_NEW3(_CLASS_, _OBJ_, _CCLASS_PRIVATE_)\
	_OBJ_ = CCLASS_NEW1(_CLASS_);\
	_CCLASS_PRIVATE_
#define CCLASS_NEW2(_CLASS_, _OBJ_) CCLASS_NEW3(_CLASS_, _OBJ_, _OBJ_->prv = new_Private())

#define CCLASS_CLASS_DEF(_NAME_, _BODY_) struct _NAME_ { _BODY_ }
#define CCLASS_CLASS2(_NAME_, _BODY_) CCLASS_CLASS_DEF(_NAME_, _BODY_)
#define CCLASS_CLASS1(_NAME_) CCLASS_CLASS2(_NAME_,)

#ifdef TCC_OVERLOADS
#	define CCLASS_NEW(...) GET_MACRO3P(__VA_ARGS__, CCLASS_NEW3, CCLASS_NEW2, CCLASS_NEW1)(__VA_ARGS__)
#	define CCLASS_CLASS(...) GET_MACRO2P(__VA_ARGS__, CCLASS_CLASS2, CCLASS_CLASS1)(__VA_ARGS__);
#else
#	define CCLASS_NEW(...) CALL_OVERLOAD(CCLASS_NEW, __VA_ARGS__)
#	define CCLASS_CLASS(...) CALL_OVERLOAD(CCLASS_CLASS, __VA_ARGS__);
#endif

#define CCLASS_DELETE(_OBJ_) NOVA_FREE(_OBJ_)
#define CCLASS_DEL(_OBJ_) CCLASS_DELETE(_OBJ_->prv); CCLASS_DELETE(_OBJ_)
#define CCLASS_FUNC(_TYPE_, _NAME_, ...) _TYPE_ (*_NAME_)(__VA_ARGS__)

#define CCLASS_INSTANTIATE(_CLASS_, _OBJ_, _VARIABLE_INITIALIZE_)\
	do\
	{\
		_OBJ_ = CCLASS_NEW1(_CLASS_);\
		\
		{\
			_VARIABLE_INITIALIZE_;\
		}\
		\
		return _OBJ_;\
	} while (0)

#define CCLASS_PRIVATE(_CCLASS_PRIVATE_VARIABLES_)\
	typedef struct Private { _CCLASS_PRIVATE_VARIABLES_ } Private;\
	\
	static Private* new_Private()\
	{\
		Private* p;\
		\
		CCLASS_INSTANTIATE(Private, p,);\
	}\
	static void del_Private(Private* p)\
	{\
		CCLASS_DELETE(p);\
	}

#endif
