<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateActivitiesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('activities', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('name');
            $table->string('description');
            $table->enum('initial_hour', [                
                '05:00 AM','06:00 AM','07:00 AM','08:00 AM',
                '09:00 AM','10:00 AM','11:00 AM','12:00 PM',
                '01:00 PM','02:00 PM','03:00 PM','04:00 PM',
                '05:00 PM','06:00 PM','07:00 PM','08:00 PM',
                '09:00 PM','10:00 PM','11:00 PM','12:00 AM'
            ])->nullable();
            $table->enum('final_hour', [                
                '05:00 AM','06:00 AM','07:00 AM','08:00 AM',
                '09:00 AM','10:00 AM','11:00 AM','12:00 PM',
                '01:00 PM','02:00 PM','03:00 PM','04:00 PM',
                '05:00 PM','06:00 PM','07:00 PM','08:00 PM',
                '09:00 PM','10:00 PM','11:00 PM','12:00 AM'
            ])->nullable();

            $table->unsignedBigInteger('area_id');
            $table->foreign('area_id')
            ->references('id')
            ->on('areas')
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
        Schema::dropIfExists('activities');
    }
}
