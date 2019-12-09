/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { CommandMemoService } from './command-memo.service';

describe('Service: CommandMemo', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CommandMemoService]
    });
  });

  it('should ...', inject([CommandMemoService], (service: CommandMemoService) => {
    expect(service).toBeTruthy();
  }));
});
