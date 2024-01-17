package com.msa.supervisor.model.data.database

import android.content.Context
import androidx.room.Room
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
import com.msa.supervisor.tool.CompanionValues
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/**
 * create by Ali Soleymani.
 */
@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {


    //---------------------------------------------------------------------------------------------- providerRoleDao
    @Provides
    @Singleton
    fun providerRoleDao(appDatabase: AppDatabase) : RoleDao {
        return appDatabase.roleDao()
    }
    //---------------------------------------------------------------------------------------------- providerRoleDao

    @Provides
    @Singleton
    fun providerCustomerDao(appDatabase: AppDatabase) : CustomerDao {
        return appDatabase.customerDao()
    }
    @Provides
    @Singleton
    fun providercustomerapprovalDaoo(appDatabase: AppDatabase) : CustomerapprovalDao {
        return appDatabase.customerapprovalDao()
    }


    @Provides
    @Singleton
    fun providerVisitors(appDatabase: AppDatabase):VisitorsDao{
        return  appDatabase.visitorsDao()
    }
    @Provides
    @Singleton
    fun providerBranches(appDatabase: AppDatabase):BranchesDao{
        return appDatabase.branchesDao()
    }
    @Provides
    @Singleton
    fun providerUserLogin(appDatabase: AppDatabase):UserLoginDao{
        return appDatabase.userLoginDao()
    }

    @Provides
    @Singleton
    fun providerProductGroup(appDatabase: AppDatabase):ProductGroupDao{
        return appDatabase.productGroupDao()
    }




    @Provides
    @Singleton
    fun providerQuestionnaireHeaderDao(appDatabase: AppDatabase): QuestionnaireHeaderDao {
        return appDatabase.questionnaireHeaderDao()
    }

    @Provides
    @Singleton
    fun providerQuestionnaireLineDao(appDatabase: AppDatabase): QuestionnaireLineDao {
        return appDatabase.questionnaireLineDao()
    }


    @Provides
    @Singleton
    fun providerControlValidatorDao(appDatabase: AppDatabase): ControlValidatorDao {
        return appDatabase.controlValidatorDao()
    }


    @Provides
    @Singleton
    fun providerQuestionnaireLineOptionDao(appDatabase: AppDatabase): QuestionnaireLineOptionDao {
        return appDatabase.questionnaireLineOptionDao()
    }


    @Provides
    @Singleton
    fun providerQuestionnaireHistoryDao(appDatabase: AppDatabase): QuestionnaireHistoryDao {
        return appDatabase.questionnaireHistoryDao()
    }


    @Provides
    @Singleton
    fun providerPinCodeConfirmDao(appDatabase: AppDatabase): PinCodeConfirmDao {
        return appDatabase.pinCodeConfirmDao()
    }
    //---------------------------------------------------------------------------------------------- provideAppDatabase
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            CompanionValues.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }
    //---------------------------------------------------------------------------------------------- provideAppDatabase

}