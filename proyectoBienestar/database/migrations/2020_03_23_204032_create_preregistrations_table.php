<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePreregistrationsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('preregistrations', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('document');
            $table->string('name');
            $table->string('email');
            $table->string('academic_program');
            $table->string('semester');

            $table->unsignedBigInteger('activity_id');
            $table->foreign('activity_id')
            ->references('id')
            ->on('activities')
            ->onDelete('cascade');  

            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('preregistrations');
    }
}
