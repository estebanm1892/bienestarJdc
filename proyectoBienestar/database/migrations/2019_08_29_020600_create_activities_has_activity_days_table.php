<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateActivitiesHasActivityDaysTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('activities_has_activity_days', function (Blueprint $table) {
            $table->bigIncrements('id');

            $table->BigInteger('activity_days_id')->unsigned();
            $table->foreign('activity_days_id')->references('id')->on('activity_days')->onDelete('cascade');

            $table->BigInteger('activities_id')->unsigned();
            $table->foreign('activities_id')->references('id')->on('activities')->onDelete('cascade');

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
        Schema::dropIfExists('activities_has_activity_days');
    }
}
