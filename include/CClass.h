//
//  CClass.h
//  CTest
//
//  Created by Braden Steffaniak on 12/27/13.
//  Copyright (c) 2013 Braden Steffaniak. All rights reserved.
//

#ifndef CClass_CClass_h
#define CClass_CClass_h

#include <stdlib.h>
#include <assert.h>

#define call(_OBJ_, _NAME_, ...) _OBJ_->_NAME_(_OBJ_, __VA_ARGS__)

#define NO_PRIVATE\
	static struct Private* new_Private()\
	{\
		return 0;\
	}\
	static void del_Private(struct Private* obj)\
	{\
		\
	}

#define NEW(_CLASS_, _OBJ_)\
	_CLASS_* _OBJ_ = NEW2(_CLASS_);\
	_OBJ_->prv = new_Private()

#define NEW2(_CLASS_) (_CLASS_*)malloc(sizeof(_CLASS_))

#define NEW_EXT(_CLASS_, _EXTENDS_, _OBJ_) NEW(_CLASS_, _OBJ_); _OBJ_->getParent = getParent

#define DEL(_OBJ_) DELETE(_OBJ_->prv); DELETE(_OBJ_)

#define DEL_EXT(_EXTENDS_, _OBJ_) del_##_EXTENDS_(_OBJ_->prv->parent); DEL(_OBJ_)

#define DELETE(_OBJ_) free(_OBJ_)

#define FUNC(_TYPE_, _NAME_, ...) _TYPE_ (*_NAME_)(__VA_ARGS__)

#define CLASS(_NAME_, _BODY_) CLASS2(_NAME_, _BODY_,);

#define CLASS2(_NAME_, _BODY_, _METHOD_PREFIX_)\
	CLASS_DEF(_NAME_, _BODY_)\
	//DECLARE_METHODS(_NAME_, _METHOD_PREFIX_)

#define CLASS3(_NAME_, _BODY_, _METHOD_PREFIX_)\
	typedef struct _NAME_\
	{\
		_BODY_\
	} _NAME_;\
	//\
	//DECLARE_METHODS(_NAME_, _METHOD_PREFIX_)

#define CLASS_EXT(_NAME_, _EXTENDS_, _BODY_) CLASS2_EXT(_NAME_, _EXTENDS_, _BODY_,);

#define CLASS2_EXT(_NAME_, _EXTENDS_, _BODY_, _METHOD_PREFIX_)\
	CLASS_EXT_DEF(_NAME_, _EXTENDS_, _BODY_)\
	//DECLARE_METHODS(_NAME_, _METHOD_PREFIX_)

#define CLASS_DEF(_NAME_, _BODY_)\
	typedef struct _NAME_ _NAME_;\
	struct _NAME_\
	{\
		_BODY_\
		struct Private* prv;\
	};

#define CLASS_EXT_DEF(_NAME_, _EXTENDS_, _BODY_)\
	typedef struct _NAME_\
	{\
		_BODY_\
		struct Private* prv;\
		FUNC(_EXTENDS_*, getParent, struct _NAME_*);\
	} _NAME_;

#define DECLARE_METHODS(_CLASS_, _METHOD_PREFIX_)\
	_METHOD_PREFIX_ _CLASS_* new_##_CLASS_();\
	_METHOD_PREFIX_ void del_##_CLASS_(_CLASS_* obj)

#define PRIVATE_EXT(_CLASS_, _EXTENDS_, ...)\
	PRIVATE(__VA_ARGS__ _EXTENDS_* parent;)\
	static _EXTENDS_* getParent(_CLASS_* o)\
	{\
		return o->prv->parent;\
	}

#define PRIVATE(_PRIVATE_VARIABLES_)\
	CLASS3(Private, _PRIVATE_VARIABLES_, static);\
	\
	static Private* new_Private()\
	{\
		INSTANTIATE(Private, p,);\
	}\
	static void del_Private(Private* p)\
	{\
		DELETE(p);\
	}

#define INSTANTIATE(_CLASS_, _OBJ_, _VARIABLE_INITIALIZE_)\
	do\
	{\
		_CLASS_* _OBJ_ = NEW2(_CLASS_);\
		assert(_OBJ_);\
		\
		{\
			_VARIABLE_INITIALIZE_;\
		}\
		\
		return _OBJ_;\
	} while (0)

#endif
