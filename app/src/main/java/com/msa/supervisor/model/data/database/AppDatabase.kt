package com.msa.supervisor.model.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.msa.supervisor.model.data.database.dao.BranchesDao
import com.msa.supervisor.model.data.database.dao.ControlValidatorDao
import com.msa.supervisor.model.data.database.dao.CustomerDao
import com.msa.supervisor.model.data.database.dao.CustomerapprovalDao
import com.msa.supervisor.model.data.database.dao.PinCodeConfirmDao
import com.msa.supervisor.model.data.database.dao.ProductGroupDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireHeaderDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireHistoryDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireLineDao
import com.msa.supervisor.model.data.database.dao.QuestionnaireLineOptionDao
import com.msa.supervisor.model.data.database.dao.RoleDao
import com.msa.supervisor.model.data.database.dao.UserLoginDao
import com.msa.supervisor.model.data.database.dao.VisitorsDao
import com.msa.supervisor.model.data.database.entity.BranchesEntity
import com.msa.supervisor.model.data.database.entity.ControlValidatorEntity
import com.msa.supervisor.model.data.database.entity.CustomerEntity
import com.msa.supervisor.model.data.database.entity.CustomerapprovalEntity
import com.msa.supervisor.model.data.database.entity.PinCodeConfirmEntity
import com.msa.supervisor.model.data.database.entity.ProductGroupEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHeaderEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireHistoryEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineEntity
import com.msa.supervisor.model.data.database.entity.QuestionnaireLineOptionEntity
import com.msa.supervisor.model.data.database.entity.RoleEntity
import com.msa.supervisor.model.data.database.entity.UserLoginEntity
import com.msa.supervisor.model.data.database.entity.VisitorsEntity
/**
 * create by Ali Soleymani.
 */

@Database(
    entities = [RoleEntity::class,
        VisitorsEntity::class,
        CustomerEntity::class,
        CustomerapprovalEntity::class,
        BranchesEntity::class,
        UserLoginEntity::class,
        ProductGroupEntity::class,
        QuestionnaireHeaderEntity::class,
        QuestionnaireLineEntity::class,
        QuestionnaireLineOptionEntity::class,
        ControlValidatorEntity::class,
        QuestionnaireHistoryEntity::class,
        PinCodeConfirmEntity::class
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roleDao(): RoleDao
    abstract fun visitorsDao(): VisitorsDao
    abstract fun customerDao(): CustomerDao
    abstract fun customerapprovalDao(): CustomerapprovalDao
    abstract fun branchesDao(): BranchesDao
    abstract fun userLoginDao(): UserLoginDao
    abstract fun productGroupDao(): ProductGroupDao

    abstract fun questionnaireHeaderDao(): QuestionnaireHeaderDao
    abstract fun questionnaireLineDao(): QuestionnaireLineDao
    abstract fun controlValidatorDao(): ControlValidatorDao
    abstract fun questionnaireLineOptionDao(): QuestionnaireLineOptionDao
    abstract fun questionnaireHistoryDao(): QuestionnaireHistoryDao

    abstract fun pinCodeConfirmDao():PinCodeConfirmDao


}