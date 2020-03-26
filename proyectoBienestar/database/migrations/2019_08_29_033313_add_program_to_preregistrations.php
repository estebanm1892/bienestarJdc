<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AddProgramToPreregistrations extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('preregistrations', function (Blueprint $table) {
            $table->unsignedBigInteger('academic_program_id');
            $table->foreign('academic_program_id')
            ->references('id')
            ->on('academic_programs')
            ->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('preregistrations', function (Blueprint $table) {
            //
        });
    }
}
